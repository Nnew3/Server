package mju.nnews3.api.controller;

import mju.nnews3.api.dto.*;
import mju.nnews3.common.Response;
import mju.nnews3.service.NewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/user/v1/main/news")
    public ResponseEntity<Response<Map<String, DateNewsRes>>> getDateNews() {
        LocalDate baseDate = LocalDate.now();
        Map<String, DateNewsRes> newsMap = new HashMap<>();

        newsMap.put("day", newsService.getBestNewsInRangeForPeriod(baseDate, "day"));
        newsMap.put("week", newsService.getBestNewsInRangeForPeriod(baseDate, "week"));
        newsMap.put("month", newsService.getBestNewsInRangeForPeriod(baseDate, "month"));

        return ResponseEntity.ok(Response.success(newsMap));
    }

    @GetMapping("/user/v1/newsDetails")
    public ResponseEntity<Response<DetailsNews>> getDetailsNews(@RequestParam Long newsId) {
        DetailsNews detailsNews = newsService.getDetailsNews(newsId);

        return ResponseEntity.ok(Response.success(detailsNews));
    }

    @GetMapping("/user/v1")
    public ResponseEntity<Response<NewsListRes>> getNewsByCategorySort(
            @RequestParam String category,
            @RequestParam String sort
    ) {
        NewsListRes newsListRes = newsService.getNewsByCategoryAndSort(category, sort);

        return ResponseEntity.ok(Response.success(newsListRes));
    }
}
