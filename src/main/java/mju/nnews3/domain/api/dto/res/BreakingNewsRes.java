package mju.nnews3.domain.api.dto.res;

import mju.nnews3.domain.api.dto.NewsDto;

import java.util.List;

public record BreakingNewsRes(
        NewsDto breakingNewsRecent,
        List<NewsDto> breakingNews
) {}
