package mju.nnews3.api.dto.res;

import java.util.List;

public record RelatedNewsListRes(
        List<RelatedNewsRes> relatedNewsResList
) {
}
