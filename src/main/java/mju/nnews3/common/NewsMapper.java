package mju.nnews3.common;

import mju.nnews3.api.dto.NewsDto;
import mju.nnews3.domain.News;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NewsMapper {
    public NewsDto toDto(News news) {
        return new NewsDto(news.getId(), news.getTitle());
    }

    public List<NewsDto> toDtoList(List<News> newsList) {
        return newsList.stream()
                .map(this::toDto)
                .toList();
    }
}
