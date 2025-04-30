package mju.nnews3.api.dto.res;

public record QuizInfoRes(
        Long userId,
        String nickname,
        int score
) {
}
