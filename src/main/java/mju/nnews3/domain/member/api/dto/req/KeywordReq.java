package mju.nnews3.domain.member.api.dto.req;

public record KeywordReq(
        Long id,
        String previousKeyword,
        String newKeyword
) {}
