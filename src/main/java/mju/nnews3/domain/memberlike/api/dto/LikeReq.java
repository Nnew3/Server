package mju.nnews3.domain.memberlike.api.dto;

public record LikeReq(
        Long userId,
        Long newsId
) {
}
