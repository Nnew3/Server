package mju.nnews3.common;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import mju.nnews3.api.dto.DateNewsRes;
import mju.nnews3.api.dto.DetailsNews;
import mju.nnews3.api.dto.NewsDto;
import mju.nnews3.domain.News;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
@Component
public class NewsMapper {
    private final ObjectMapper objectMapper;

    public NewsMapper(ObjectMapper objectMapper) {
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
        return new DateNewsRes(
                news.getId(),
                news.getTitle(),
                news.getSummary(),
                DateUtil.formatDate(news.getDate()),
                news.getImgUrl()
        );
    }

    public DetailsNews toDetailsDto(News news) {
        List<String> summaryList;
        try {
            summaryList = objectMapper.readValue(news.getSummary(), new TypeReference<>() {});
        } catch (IOException e) {
            throw new RuntimeException("Summary 파싱 실패", e);
        }

        return new DetailsNews(
                news.getId(),
                news.getTitle(),
                summaryList,
                news.getImgUrl(),
                news.getLink()
        );
    }
}
