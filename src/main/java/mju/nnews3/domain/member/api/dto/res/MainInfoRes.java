package mju.nnews3.domain.member.api.dto.res;

public record MainInfoRes(
        String nickname,
        String todayDate,
        String weather
) {
}
