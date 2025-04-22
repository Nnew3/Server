package mju.nnews3.api.controller;

import mju.nnews3.api.dto.req.KeywordReq;
import mju.nnews3.api.dto.res.MainInfoRes;
import mju.nnews3.common.Response;
import mju.nnews3.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
