package mju.nnews3.api.dto.res;

import mju.nnews3.api.dto.NewsDto;

import java.util.List;

public record BreakingNewsRes(
        NewsDto breakingNewsRecent,
        List<NewsDto> breakingNews
) {}
