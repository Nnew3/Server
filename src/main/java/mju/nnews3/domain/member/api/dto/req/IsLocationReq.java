package mju.nnews3.domain.member.api.dto.req;

public record IsLocationReq(
        Long userId,
        boolean isLocation
) {

}
