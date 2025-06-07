package mju.nnews3.domain.api.dto.req;

public record QuizScoreReq(
        Long userId,
        Integer score
) {
}
