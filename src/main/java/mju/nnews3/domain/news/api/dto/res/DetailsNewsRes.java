package mju.nnews3.domain.news.api.dto.res;

public record DetailsNewsRes(
        Long id,
        String title,
        String summary,
        String imgUrl,
        String link
) {
}
