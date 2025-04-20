package mju.nnews3.api.controller;

import mju.nnews3.api.dto.BreakingNewsRes;
import mju.nnews3.common.Response;
import mju.nnews3.service.NewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NewsController {
    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/user/v1/main/breakingNews")
    public ResponseEntity<Response<BreakingNewsRes>> getBreakingNews() {
        BreakingNewsRes breakingNewsRes = newsService.getBreakingNews();
        return ResponseEntity.ok(Response.success(breakingNewsRes));
    }
}
