package mju.nnews3.domain.member.api.dto.req;

public record QuizScoreReq(
        Long userId,
        Integer score
) {
}
