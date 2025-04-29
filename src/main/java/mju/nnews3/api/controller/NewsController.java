package mju.nnews3.api.controller;

import mju.nnews3.api.dto.res.*;
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
    public ResponseEntity<Response<DetailsNewsRes>> getDetailsNews(@RequestParam Long newsId) {
        DetailsNewsRes detailsNewsRes = newsService.getDetailsNews(newsId);

        return ResponseEntity.ok(Response.success(detailsNewsRes));
    }

    @GetMapping("/user/v1")
    public ResponseEntity<Response<NewsListRes>> getNewsByCategorySort(
            @RequestParam String category,
            @RequestParam String sort
    ) {
        Long memberId = 1L; // 추후 토큰 기반으로 대체
        NewsListRes newsListRes;

        if ("keyword".equalsIgnoreCase(category)) {
            newsListRes = newsService.getNewsByMemberKeywords(memberId, sort);
        } else {
            newsListRes = newsService.getNewsByCategoryAndSort(category, sort);
        }

        return ResponseEntity.ok(Response.success(newsListRes));
    }

    @GetMapping("/user/v1/main/userNews")
    public ResponseEntity<Response<NewsListRes>> getNewsByKeywordOrRecent() {
        Long memberId = 1L; // 추후 토큰 기반으로 교체
        NewsListRes newsListRes = newsService.getNewsByKeywordOrRecent(memberId);
        return ResponseEntity.ok(Response.success(newsListRes));
    }

    @GetMapping("/user/v1/relatedNews")
    public ResponseEntity<Response<RelatedNewsListRes>> getNewsRelatedNews(@RequestParam Long newsId) {
        RelatedNewsListRes relatedNewsListRes = newsService.getNewsRelatedNews(newsId);

        return ResponseEntity.ok(Response.success(relatedNewsListRes));
    }
}
