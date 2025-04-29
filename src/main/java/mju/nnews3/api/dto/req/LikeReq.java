package mju.nnews3.api.dto.req;

public record LikeReq(
        Long userId,
        Long newsId
) {
}
