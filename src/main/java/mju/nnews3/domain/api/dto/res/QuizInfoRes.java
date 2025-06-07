package mju.nnews3.domain.api.dto.res;

public record QuizInfoRes(
        Long userId,
        String nickname,
        int score
) {
}
