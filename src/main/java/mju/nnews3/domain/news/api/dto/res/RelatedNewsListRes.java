package mju.nnews3.domain.news.api.dto.res;

import java.util.List;

public record RelatedNewsListRes(
        String word,
        List<RelatedNewsRes> relatedNewsResList
) {
}
