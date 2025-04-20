package mju.nnews3.service;

import mju.nnews3.api.dto.BreakingNewsRes;
import mju.nnews3.api.dto.NewsDto;
import mju.nnews3.common.NewsMapper;
import mju.nnews3.domain.News;
import mju.nnews3.domain.repository.NewsRepository;
import mju.nnews3.execption.NotFoundNewsException;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
