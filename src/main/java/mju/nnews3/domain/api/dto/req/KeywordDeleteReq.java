package mju.nnews3.domain.api.dto.req;

public record KeywordDeleteReq (
        Long userId,
        String keyword
) {
}
