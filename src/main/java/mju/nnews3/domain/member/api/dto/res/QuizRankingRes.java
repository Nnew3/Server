package mju.nnews3.domain.member.api.dto.res;

public record QuizRankingRes(
        Long userId,
        Long ranking,
        String nickname,
        int score
) {
}
