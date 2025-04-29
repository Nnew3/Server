package mju.nnews3.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import mju.nnews3.api.dto.res.BreakingNewsRes;
import mju.nnews3.api.dto.res.DateNewsRes;
import mju.nnews3.api.dto.res.DetailsNewsRes;
import mju.nnews3.api.dto.res.NewsListRes;
import mju.nnews3.common.DateUtil;
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
import java.util.stream.Stream;

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

}
