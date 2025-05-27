package mju.nnews3.api.dto.req;

import jakarta.validation.constraints.NotBlank;

public record PostTokenReq(
        @NotBlank(message = "토큰을 입력해주세요.")
        String token
) {
}
