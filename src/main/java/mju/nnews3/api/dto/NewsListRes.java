package mju.nnews3.api.dto;

import java.util.List;
public record NewsListRes(
        List<NewsRes> newsResList
) {
}
