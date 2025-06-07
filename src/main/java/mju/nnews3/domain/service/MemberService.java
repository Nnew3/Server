package mju.nnews3.domain.service;

import mju.nnews3.domain.Member;
import mju.nnews3.domain.News;
import mju.nnews3.domain.api.dto.req.*;
import mju.nnews3.domain.api.dto.res.*;
import mju.nnews3.domain.repository.MemberLikeRepository;
import mju.nnews3.domain.repository.NewsRepository;
import mju.nnews3.domain.repository.ViewRepository;
import mju.nnews3.domain.repository.MemberRepository;
import mju.nnews3.execption.BusinessException;
import mju.nnews3.execption.error.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final WeatherService weatherService;
    private final NewsRepository newsRepository;
    private final ViewRepository viewRepository;
    private final MemberLikeRepository memberLikeRepository;

    public MemberService(MemberRepository memberRepository, WeatherService weatherService, NewsRepository newsRepository, ViewRepository viewRepository, MemberLikeRepository memberLikeRepository) {
        this.memberRepository = memberRepository;
        this.weatherService = weatherService;
        this.newsRepository = newsRepository;
        this.viewRepository = viewRepository;
        this.memberLikeRepository = memberLikeRepository;
    }

    public MainInfoRes getMainInfo(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));

        String today = LocalDate.now().toString();

        String weather = weatherService.getWeatherText(
                member.getLatitude(),
                member.getLongitude(),
                member.isLocation()
        );

        return new MainInfoRes(member.getNickname(), today, weather);
    }

    @Transactional
    public void updateKeyword(KeywordReq keywordReq) {
        if (keywordReq.newKeyword() != null) {
            String newKeyword = keywordReq.newKeyword();
            if (newKeyword.trim().isEmpty() || newKeyword.length() > 10) {
                throw new BusinessException(ErrorCode.KEYWORD_TOO_LONG_WHITESPACE);
            }
        }

        Member member = memberRepository.findById(keywordReq.id())
                .orElseThrow(()->new BusinessException(ErrorCode.NOT_FOUND_USER));

        String previousKeyword = keywordReq.previousKeyword();
        String newKeyword = keywordReq.newKeyword();

        if (previousKeyword != null && newKeyword != null) {
            member.updateKeywordList(previousKeyword, newKeyword);
        }
    }

    public MypageRes getMaypage(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));

        return new MypageRes(member.getId(), member.getNickname(), member.getEmail(), member.getKeyword(), member.getScore(), member.isAlarm(), member.isLocation());
    }

    public MemberNewsListRes getRecentNewsByUserId(Long userId) {
        List<News> recentNews = viewRepository.findViewedNewsByUserId(userId);
        return mapToMemberNewsListRes(recentNews);
    }

    public MemberNewsListRes getLikedNewsByUserId(Long userId) {
        List<News> likedNews = memberLikeRepository.findLikedNewsByUserId(userId);
        return mapToMemberNewsListRes(likedNews);
    }

    private MemberNewsListRes mapToMemberNewsListRes(List<News> newsList) {
        List<MemberNewsRes> resList = newsList.stream()
                .map(MemberNewsRes::from)
                .toList();

        return new MemberNewsListRes(resList);
    }

    @Transactional
    public void patchLocation(IsLocationReq isLocationReq) {
        Member member = memberRepository.findById(isLocationReq.userId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));

        member.changeLocationConsent(isLocationReq.isLocation());
    }

    @Transactional
    public void patchAlarm(AlarmReq alarmReq) {
        Member member = memberRepository.findById(alarmReq.userId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));

        member.changeAlarmConsent(alarmReq.isAlarm());
    }

    public QuizInfoRes getQuizInfo(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));

        return new QuizInfoRes(member.getId(), member.getNickname(), member.getSafeScore());
    }

    public QuizRankinListRes getQuizRanking() {
        List<Member> members = memberRepository.findAll();

        List<Member> sortedMembers = members.stream()
                .sorted((m1, m2) -> Integer.compare(m2.getSafeScore(), m1.getSafeScore()))
                .collect(Collectors.toList());

        List<QuizRankingRes> rankingList = new ArrayList<>();
        for (int i = 0; i < sortedMembers.size(); i++) {
            Member member = sortedMembers.get(i);
            rankingList.add(new QuizRankingRes(
                    member.getId(),
                    (long) (i + 1),
                    member.getNickname(),
                    member.getSafeScore()
            ));
        }

        return new QuizRankinListRes(rankingList);
    }

    @Transactional
    public void updateScore(QuizScoreReq quizScoreReq) {
        Member member = memberRepository.findById(quizScoreReq.userId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));

        member.updateScore(quizScoreReq.score());
    }

    @Transactional
    public void deleteKeyword(KeywordDeleteReq keywordDeleteReq) {
        Member member = memberRepository.findById(keywordDeleteReq.userId())
                .orElseThrow(()->new BusinessException(ErrorCode.NOT_FOUND_USER));

        member.deleteKeyword(keywordDeleteReq.keyword());
    }

    @Transactional
    public void updateLocation(LocationReq locationReq) {
        Member member = memberRepository.findById(locationReq.userId())
                .orElseThrow(()->new BusinessException(ErrorCode.NOT_FOUND_USER));

        member.updateLocation(locationReq.lat(), locationReq.lon());
    }
}
