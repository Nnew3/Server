package mju.nnews3.api.controller;

import mju.nnews3.api.dto.req.*;
import mju.nnews3.api.dto.res.*;
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

    @PatchMapping("/user/v1/location")
    public ResponseEntity<Response<Void>> patchLocation(@RequestBody LocationReq locationReq) {
        memberService.patchLocation(locationReq);

        return ResponseEntity.ok(Response.success(null));
    }

    @PatchMapping("/user/v1/alarm")
    public ResponseEntity<Response<Void>> patchAlarm(@RequestBody AlarmReq alarmReq) {
        memberService.patchAlarm(alarmReq);

        return ResponseEntity.ok(Response.success(null));
    }

    @GetMapping("/user/v1/quiz/info")
    public ResponseEntity<Response<QuizInfoRes>> getQuizInfo() {
        int userId = 1;
        Long longUserId = Long.valueOf(userId);

        QuizInfoRes quizInfoRes = memberService.getQuizInfo(longUserId);

        return ResponseEntity.ok(Response.success(quizInfoRes));
    }

    @GetMapping("/user/v1/quiz/ranking")
    public ResponseEntity<Response<QuizRankinListRes>> getQuizRanking() {
        QuizRankinListRes quizRankinListRes = memberService.getQuizRanking();

        return ResponseEntity.ok(Response.success(quizRankinListRes));
    }

    @PostMapping("/user/v1/quiz/score")
    public ResponseEntity<Response<Void>> updateScore(@RequestBody QuizScoreReq quizScoreReq) {
        memberService.updateScore(quizScoreReq);

        return ResponseEntity.ok(Response.success(null));
    }

    @DeleteMapping("/user/v1/keyword")
    public ResponseEntity<Response<Void>> deleteKeyword(@RequestBody KeywordDeleteReq keywordDeleteReq) {
        memberService.deleteKeyword(keywordDeleteReq);

        return ResponseEntity.ok(Response.success(null));
    }
}
