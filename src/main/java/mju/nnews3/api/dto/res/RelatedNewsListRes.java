package mju.nnews3.api.dto.res;

import java.util.List;

public record RelatedNewsListRes(
        String word,
        List<RelatedNewsRes> relatedNewsResList
) {
}
