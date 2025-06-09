package mju.nnews3.domain.member.api.dto.res;

public record QuizInfoRes(
        Long userId,
        String nickname,
        int score
) {
}
