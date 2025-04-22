package mju.nnews3.api.dto;

import java.util.List;

public record DetailsNews(
        Long id,
        String title,
        String summary,
        String imgUrl,
        String link
) {
}
