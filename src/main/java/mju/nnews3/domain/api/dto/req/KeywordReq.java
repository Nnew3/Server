package mju.nnews3.domain.api.dto.req;

public record KeywordReq(
        Long id,
        String previousKeyword,
        String newKeyword
) {}
