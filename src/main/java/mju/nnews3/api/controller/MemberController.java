package mju.nnews3.api.controller;

import mju.nnews3.api.dto.req.KeywordReq;
import mju.nnews3.api.dto.res.MainInfoRes;
import mju.nnews3.api.dto.res.MemberNewsListRes;
import mju.nnews3.api.dto.res.MypageRes;
import mju.nnews3.common.Response;
import mju.nnews3.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/user/v1/main/info")
    public ResponseEntity<Response<MainInfoRes>> getMainInfo() {
        int userId = 1;
        Long longUserId = Long.valueOf(userId);

        MainInfoRes mainInfoRes = memberService.getMainInfo(longUserId);


        return ResponseEntity.ok(Response.success(mainInfoRes));
    }

    @PostMapping("/user/v1/keyword")
    public ResponseEntity<Response<Void>> updateKeyword(
            @RequestBody KeywordReq keywordReq
    ) {
        memberService.updateKeyword(keywordReq);

        return ResponseEntity.ok(Response.success(null));
    }

    @GetMapping("/user/v1/mypage/info")
    public ResponseEntity<Response<MypageRes>> getMaypage() {
        int userId = 1;
        Long longUserId = Long.valueOf(userId);

        MypageRes mypageRes = memberService.getMaypage(longUserId);

        return ResponseEntity.ok(Response.success(mypageRes));
    }

    @GetMapping("/user/v1/mypage")
    public ResponseEntity<Response<MemberNewsListRes>> getMemberNewsList(
            @RequestParam Long userId,
            @RequestParam String type
    ) {
        MemberNewsListRes memberNewsListRes;

        if ("recent".equalsIgnoreCase(type)) {
            memberNewsListRes = memberService.getRecentNewsByUserId(userId);
        } else if ("like".equalsIgnoreCase(type)) {
            memberNewsListRes = memberService.getLikedNewsByUserId(userId);
        } else {
            return ResponseEntity.badRequest()
                    .body(Response.fail(HttpStatus.BAD_REQUEST, "잘못된 type 파라미터입니다. (recent 또는 like)"));
        }

        return ResponseEntity.ok(Response.success(memberNewsListRes));
    }
}
