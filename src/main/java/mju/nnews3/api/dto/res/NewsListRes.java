package mju.nnews3.api.dto.res;

import java.util.List;
public record NewsListRes(
        List<NewsRes> newsResList
) {
}
