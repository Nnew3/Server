package mju.nnews3.domain.member.api.dto.req;

public record KeywordDeleteReq (
        Long userId,
        String keyword
) {
}
