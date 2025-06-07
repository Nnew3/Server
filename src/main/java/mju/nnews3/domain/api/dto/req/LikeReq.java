package mju.nnews3.domain.api.dto.req;

public record LikeReq(
        Long userId,
        Long newsId
) {
}
