package mju.nnews3.api.dto.req;

public record AlarmReq(
        Long userId,
        boolean isAlarm
) {
}
