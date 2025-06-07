package mju.nnews3.domain.api.controller;

import mju.nnews3.domain.api.dto.req.LikeReq;
import mju.nnews3.common.Response;
import mju.nnews3.domain.service.MemberLikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberLikeController {
    private final MemberLikeService memberLikeService;

    public MemberLikeController(MemberLikeService memberLikeService) {
        this.memberLikeService = memberLikeService;
    }

    @PostMapping("/user/v1/news/like")
    public ResponseEntity<Response<Void>> updateLike(
            @RequestBody LikeReq likeReq
    ) {
        memberLikeService.updateLike(likeReq);

        return ResponseEntity.ok(Response.success(null));
    }

    @DeleteMapping("/user/v1/news/like")
    public ResponseEntity<Response<Void>> deleteLike(
            @RequestBody LikeReq likeReq
    ) {
        memberLikeService.deleteLike(likeReq);

        return ResponseEntity.ok(Response.success(null));
    }
}
