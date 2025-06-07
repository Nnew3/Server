package mju.nnews3.domain.api.dto.req;

public record LocationReq(
        Long userId,
        Double lat,
        Double lon
) {

}
