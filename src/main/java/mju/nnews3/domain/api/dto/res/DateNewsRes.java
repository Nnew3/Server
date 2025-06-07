package mju.nnews3.domain.api.dto.res;

public record DateNewsRes(
        Long id,
        String title,
        String content,
        String date,
        String imgUrl
) {
}
