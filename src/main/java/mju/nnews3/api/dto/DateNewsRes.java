package mju.nnews3.api.dto;

public record DateNewsRes(
        Long id,
        String title,
        String content,
        String date,
        String imgUrl
) {
}
