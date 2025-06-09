package mju.nnews3.domain.member.api.dto.res;

import java.util.List;

public record MemberNewsListRes(
        List<MemberNewsRes> newsList
) {
}
