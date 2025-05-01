package mju.nnews3.api.dto.req;

public record KeywordDeleteReq (
        Long userId,
        String keyword
) {
}
