package mju.nnews3.domain.api.dto.res;

import mju.nnews3.domain.News;

public record MemberNewsRes(
        Long id,
        String title,
        String publisher,
        String imgUrl
) {
    public static MemberNewsRes from(News news) {
        return new MemberNewsRes(
                news.getId(),
                news.getTitle(),
                news.getSummary(),
                news.getImgUrl()
        );
    }
}
