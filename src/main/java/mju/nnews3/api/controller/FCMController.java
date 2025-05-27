package mju.nnews3.api.controller;

import jakarta.validation.Valid;
import mju.nnews3.api.dto.req.BreakingNewsReq;
import mju.nnews3.api.dto.req.PostTokenReq;
import mju.nnews3.common.Response;
import mju.nnews3.service.FCMService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FCMController {
    private final FCMService fcmService;

    public FCMController(FCMService fcmService) {
        this.fcmService = fcmService;
    }

    @PostMapping("/user/{id}/token")
    public ResponseEntity<Response<Void>> registerToken(
            @PathVariable Long id,
            @Valid @RequestBody PostTokenReq postTokenReq
    ) {
        fcmService.registerToken(id, postTokenReq.token());
        return ResponseEntity.ok(Response.success(null));
    }

    @PostMapping("/breakingNews")
    public ResponseEntity<Response<Void>> sendBreakingNews(@RequestBody BreakingNewsReq breakingNewsReq) {
        fcmService.sendBreakingNews(breakingNewsReq.title());
        return ResponseEntity.ok(Response.success(null));
    }
}
