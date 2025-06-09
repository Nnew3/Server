package mju.nnews3.domain.member.api.dto.req;

public record AlarmReq(
        Long userId,
        boolean isAlarm
) {
}
