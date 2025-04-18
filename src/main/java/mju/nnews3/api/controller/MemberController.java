package mju.nnews3.api.controller;

import mju.nnews3.api.dto.MainInfoRes;
import mju.nnews3.common.Response;
import mju.nnews3.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/user/v1/main/info")
    public ResponseEntity<Response<MainInfoRes>> getMainInfo(@RequestParam Long userId) {
        MainInfoRes mainInfoRes = memberService.getMainInfo(userId);

        return ResponseEntity.ok(Response.success(mainInfoRes));
    }
}
