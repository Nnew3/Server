package mju.nnews3.domain.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import mju.nnews3.api.dto.*;
import mju.nnews3.common.DateUtil;
import mju.nnews3.common.FirstSentenceExtractor;
import mju.nnews3.domain.News;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NewsMapper {

    private final ObjectMapper objectMapper;
    private final FirstSentenceExtractor firstSentenceExtractor;

    public NewsMapper(ObjectMapper objectMapper, FirstSentenceExtractor firstSentenceExtractor) {
        this.firstSentenceExtractor = firstSentenceExtractor;
        this.objectMapper = objectMapper;
    }

    public NewsDto toDto(News news) {
        return new NewsDto(news.getId(), news.getTitle());
    }

    public List<NewsDto> toDtoList(List<News> newsList) {
        return newsList.stream()
                .map(this::toDto)
                .toList();
    }

    public DateNewsRes toDateNewsRes(News news) {
        String summary = news.getSummary();

        return new DateNewsRes(
                news.getId(),
                news.getTitle(),
                (summary != null && !summary.isBlank()) ? firstSentenceExtractor.extract(summary) : null,
                DateUtil.formatDate(news.getDate()),
                news.getImgUrl()
        );
    }

    public DetailsNews toDetailsDto(News news) {
       return new DetailsNews(
                news.getId(),
                news.getTitle(),
                news.getSummary(),
                news.getImgUrl(),
                news.getLink()
        );
    }

    public NewsRes toNewsRes(News news) {
        return new NewsRes(
                news.getId(),
                news.getTitle(),
                firstSentenceExtractor.extract(news.getSummary()),
                news.getPublisher(),
                DateUtil.formatDate(news.getDate()),
                news.getImgUrl()
        );
    }

    public NewsListRes toNewsListRes(List<News> newsList) {
        List<NewsRes> newsResList = newsList.stream()
                .map(this::toNewsRes)
                .toList();

        return new NewsListRes(newsResList);
    }
}
