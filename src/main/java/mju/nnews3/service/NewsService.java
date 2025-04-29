package mju.nnews3.service;

import mju.nnews3.api.dto.res.*;
import mju.nnews3.common.util.DateUtil;
import mju.nnews3.common.util.JaccardSimilarity;
import mju.nnews3.domain.Member;
import mju.nnews3.domain.mapper.CategoryType;
import mju.nnews3.domain.mapper.NewsMapper;
import mju.nnews3.domain.News;
import mju.nnews3.domain.mapper.SortType;
import mju.nnews3.domain.repository.MemberRepository;
import mju.nnews3.domain.repository.NewsRepository;
import mju.nnews3.execption.NotFoundNewsException;
import mju.nnews3.execption.NotFoundUserException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NewsService {
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final MemberRepository memberRepository;

    public NewsService(NewsRepository newsRepository, NewsMapper newsMapper, MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
        this.newsRepository = newsRepository;
        this.newsMapper = newsMapper;
    }

    public BreakingNewsRes getBreakingNews() {
        String word = "[속보]";

        News recent = newsRepository.findFirstByTitleContainingOrderByDateDesc(word)
                .orElseThrow(NotFoundNewsException::new);

        List<News> others = newsRepository.findByTitleContainingOrderByDateDesc(word);

        return new BreakingNewsRes(
                newsMapper.toDto(recent),
                newsMapper.toDtoList(others)
        );
    }

    public DateNewsRes getBestNewsInRangeForPeriod(LocalDate baseDate, String period) {
        switch (period.toLowerCase()) {
            case "day":
                return getBestNewsInRange(
                        DateUtil.startOfDay(baseDate),
                        DateUtil.endOfDay(baseDate)
                );
            case "week":
                return getBestNewsInRange(
                        DateUtil.startOfWeek(baseDate),
                        DateUtil.endOfWeek(baseDate)
                );
            case "month":
                return getBestNewsInRange(
                        DateUtil.startOfMonth(baseDate),
                        DateUtil.endOfMonth(baseDate)
                );
            default:
                throw new NotFoundNewsException();
        }
    }

    private DateNewsRes getBestNewsInRange(LocalDateTime start, LocalDateTime end) {
        Date startDate = Date.from(start.atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(end.atZone(ZoneId.systemDefault()).toInstant());

        return newsRepository.findTopByDateBetweenOrderByViewDescDateDesc(startDate, endDate)
                .map(newsMapper::toDateNewsRes)
                .orElseThrow(() -> {
                    return new NotFoundNewsException();
                });
    }


    public DetailsNewsRes getDetailsNews(Long newsId) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(NotFoundNewsException::new);

        return newsMapper.toDetailsDto(news);
    }
    public NewsListRes getNewsByCategoryAndSort(String categoryParam, String sortParam) {
        CategoryType categoryType = CategoryType.fromParam(categoryParam);
        Sort sort = SortType.fromParam(sortParam).getSort();

        List<News> newsList = newsRepository.findByCategory(categoryType.getDisplay(), sort);

        return newsMapper.toNewsListRes(newsList);
    }

    public NewsListRes getNewsByMemberKeywords(Long memberId, String sortParam) {
        Sort sort = SortType.fromParam(sortParam).getSort();

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundUserException());

        List<String> keywords = new ArrayList<>();

        if (member.getKeyword() != null && !member.getKeyword().isEmpty()) {
            keywords = Arrays.asList(member.getKeyword().split(","));
            keywords = keywords.stream().map(String::trim).collect(Collectors.toList());
        }

        if (keywords.isEmpty()) {
            return new NewsListRes(List.of());
        }

        List<News> allNews = new ArrayList<>();
        for (String keyword : keywords) {
            List<News> keywordNews = newsRepository.findByTitleContaining(keyword, sort);
            allNews.addAll(keywordNews);
        }

        return newsMapper.toNewsListRes(allNews);
    }

    public NewsListRes getNewsByKeywordOrRecent(Long memberId) {
        NewsListRes keywordNews = getNewsByMemberKeywords(memberId, "recent");

        if (keywordNews.newsResList().isEmpty()) {
            List<News> recentNews = newsRepository.findTop10ByOrderByDateDesc();
            return newsMapper.toNewsListRes(recentNews);
        }

        return keywordNews;
    }

    public RelatedNewsListRes getNewsRelatedNews(Long newsId) {
        News targetNews = newsRepository.findById(newsId)
                .orElseThrow(NotFoundNewsException::new);

        String targetCategory = targetNews.getCategory();

        List<News> allNews = newsRepository.findAll();

        List<RelatedNewsRes> related = allNews.stream()
                .filter(n -> !n.getId().equals(newsId))
                .filter(n -> n.getCategory().equals(targetCategory))
                .map(n -> new Object() {
                    final News news = n;
                    final double similarity = JaccardSimilarity.compute(targetNews.getTitle(), n.getTitle());
                })
                .filter(wrapper -> wrapper.similarity > 0)
                .sorted((a, b) -> Double.compare(b.similarity, a.similarity))
                .limit(3)
                .map(wrapper -> new RelatedNewsRes(wrapper.news.getId(), wrapper.news.getTitle()))
                .toList();

        return new RelatedNewsListRes(related);
    }
}
