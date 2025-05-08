package mju.nnews3.api.dto.req;

public record LocationReq(
        Long userId,
        Double lat,
        Double lon
) {

}
