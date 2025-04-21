package mju.nnews3.service;

import mju.nnews3.api.dto.BreakingNewsRes;
import mju.nnews3.api.dto.DateNewRes;
import mju.nnews3.common.DateUtil;
import mju.nnews3.common.NewsMapper;
import mju.nnews3.domain.News;
import mju.nnews3.domain.repository.NewsRepository;
import mju.nnews3.execption.NotFoundNewsException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Service
public class NewsService {
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;

    public NewsService(NewsRepository newsRepository, NewsMapper newsMapper) {
        this.newsMapper = newsMapper;
        this.newsRepository = newsRepository;
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

    public DateNewRes getBestNewsInRangeForPeriod(LocalDate baseDate, String period) {
        switch (period.toLowerCase()) {
            case "day":
                return getBestNewsInRange(
                        DateUtil.startOfDay(baseDate),
                        DateUtil.endOfDay(baseDate),
                        null
                );
            case "week":
                return getBestNewsInRange(
                        DateUtil.startOfWeek(baseDate),
                        DateUtil.endOfWeek(baseDate),
                        null
                );
            case "month":
                return getBestNewsInRange(
                        DateUtil.startOfMonth(baseDate),
                        DateUtil.endOfMonth(baseDate),
                        null
                );
            default:
                throw new IllegalArgumentException("Invalid period: " + period);
        }
    }

    private DateNewRes getBestNewsInRange(LocalDateTime start, LocalDateTime end, Supplier<DateNewRes> fallback) {
        return newsRepository.findTopByDateBetweenOrderByViewDescDateDesc(start, end)
                .map(news -> new DateNewRes(
                        news.getId(),
                        news.getTitle(),
                        news.getSummary(),
                        DateUtil.formatDate(news.getDate()),
                        news.getImgUrl()
                ))
                .orElseGet(() -> fallback != null ? fallback.get() : null);
    }
}