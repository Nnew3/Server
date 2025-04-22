package mju.nnews3.api.dto.req;

public record KeywordReq(
        Long id,
        String previousKeyword,
        String newKeyword
) {}
