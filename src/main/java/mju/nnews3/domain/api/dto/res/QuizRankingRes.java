package mju.nnews3.domain.api.dto.res;

public record QuizRankingRes(
        Long userId,
        Long ranking,
        String nickname,
        int score
) {
}
