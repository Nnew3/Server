package mju.nnews3.domain.news.core.service;

import mju.nnews3.domain.news.api.dto.res.DateNewsRes;
import mju.nnews3.domain.news.api.dto.res.*;
import mju.nnews3.domain.news.core.News;
import mju.nnews3.domain.news.core.mapper.NewsMapper;
import mju.nnews3.domain.news.core.repository.NewsRepository;
import mju.nnews3.execption.BusinessException;
import mju.nnews3.execption.error.ErrorCode;
import mju.nnews3.util.DateUtil;
import mju.nnews3.util.JaccardSimilarity;
import mju.nnews3.domain.member.core.Member;
import mju.nnews3.common.mapper.CategoryType;
import mju.nnews3.common.mapper.SortType;
import mju.nnews3.domain.member.core.repository.MemberRepository;
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
                .orElseThrow(()->new BusinessException(ErrorCode.NOT_FOUND_NEWS));

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
                throw new BusinessException(ErrorCode.NOT_FOUND_NEWS);
        }
    }

    private DateNewsRes getBestNewsInRange(LocalDateTime start, LocalDateTime end) {
        Date startDate = Date.from(start.atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(end.atZone(ZoneId.systemDefault()).toInstant());

        return newsRepository.findTopByDateBetweenOrderByViewDescDateDesc(startDate, endDate)
                .map(newsMapper::toDateNewsRes)
                .orElseThrow(() -> {
                    return new BusinessException(ErrorCode.NOT_FOUND_NEWS);
                });
    }


    public DetailsNewsRes getDetailsNews(Long newsId) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(()->new BusinessException(ErrorCode.NOT_FOUND_NEWS));

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
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));

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
                .orElseThrow(()->new BusinessException(ErrorCode.NOT_FOUND_NEWS));

        String targetCategory = targetNews.getCategory();

        List<News> allNews = newsRepository.findAll();

        List<RelatedWrapper> related = allNews.stream()
                .filter(n -> !n.getId().equals(newsId))
                .filter(n -> n.getCategory().equals(targetCategory))
                .map(n -> {
                    JaccardSimilarity.Result result = JaccardSimilarity.compute(targetNews.getTitle(), n.getTitle());
                    return new RelatedWrapper(n, result.similarity(), result.commonWords());
                })
                .sorted((a, b) -> Double.compare(b.similarity, a.similarity))
                .limit(3)
                .toList();

        List<RelatedNewsRes> relatedRes = related.stream()
                .map(wrapper -> new RelatedNewsRes(wrapper.news.getId(), wrapper.news.getTitle()))
                .toList();

        String keyword = related.isEmpty() || related.get(0).commonWords.isEmpty()
                ? ""
                : related.get(0).commonWords.iterator().next();

        return new RelatedNewsListRes(keyword, relatedRes);
    }

    private static class RelatedWrapper {
        final News news;
        final double similarity;
        final Set<String> commonWords;

        RelatedWrapper(News news, double similarity, Set<String> commonWords) {
            this.news = news;
            this.similarity = similarity;
            this.commonWords = commonWords;
        }
    }

}
