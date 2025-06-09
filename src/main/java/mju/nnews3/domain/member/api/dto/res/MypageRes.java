package mju.nnews3.domain.member.api.dto.res;

public record MypageRes(
        Long id,
        String nickName,
        String email,
        String keyword,
        int score,
        boolean alarm,
        boolean location
) {
}
