package mju.nnews3.domain.member.api.dto.req;

public record LocationReq(
        Long userId,
        Double lat,
        Double lon
) {

}
