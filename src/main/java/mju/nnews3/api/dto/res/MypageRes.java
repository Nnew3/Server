package mju.nnews3.api.dto.res;

public record MypageRes(
        Long id,
        String nickName,
        String email,
        String keyword,
        boolean alarm,
        boolean location
) {
}
