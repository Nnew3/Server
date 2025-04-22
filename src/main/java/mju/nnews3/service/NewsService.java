package mju.nnews3.service;

import mju.nnews3.api.dto.*;
import mju.nnews3.common.DateUtil;
import mju.nnews3.common.FirstSentenceExtractor;
import mju.nnews3.domain.mapper.CategoryType;
import mju.nnews3.domain.mapper.NewsMapper;
import mju.nnews3.domain.News;
import mju.nnews3.domain.mapper.SortType;
import mju.nnews3.domain.repository.NewsRepository;
import mju.nnews3.execption.NotFoundNewsException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsService {
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final FirstSentenceExtractor firstSentenceExtractor;

    public NewsService(NewsRepository newsRepository, NewsMapper newsMapper, FirstSentenceExtractor firstSentenceExtractor) {
        this.firstSentenceExtractor = firstSentenceExtractor;
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


    public DetailsNews getDetailsNews(Long newsId) {
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
}
