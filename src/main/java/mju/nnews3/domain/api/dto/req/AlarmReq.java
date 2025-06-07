package mju.nnews3.domain.api.dto.req;

public record AlarmReq(
        Long userId,
        boolean isAlarm
) {
}
