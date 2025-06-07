package mju.nnews3.domain.service;

import mju.nnews3.domain.News;
import mju.nnews3.domain.repository.NewsRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class NewsFCMScheduler {
    private final NewsRepository newsRepository;
    private final FCMService fcmService;

    public NewsFCMScheduler(NewsRepository newsRepository, FCMService fcmService) {
        this.newsRepository = newsRepository;
        this.fcmService = fcmService;
    }

    @Scheduled(fixedDelay = 10000)
    @Transactional(readOnly = true)
    public void sendBreakingNewsNotifications() {
        List<News> breakingNewsList = newsRepository.findByTitle("속보");

        for (News news : breakingNewsList) {
            fcmService.sendBreakingNews(news.getTitle());
            System.out.println("속보 FCM 전송: " + news.getTitle());
        }
    }
}
