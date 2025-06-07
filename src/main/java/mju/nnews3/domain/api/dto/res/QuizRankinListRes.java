package mju.nnews3.domain.api.dto.res;

import java.util.List;

public record QuizRankinListRes(
        List<QuizRankingRes> ranking
) {
}
