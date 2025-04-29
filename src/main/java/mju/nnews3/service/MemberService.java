package mju.nnews3.service;

import mju.nnews3.api.dto.req.KeywordReq;
import mju.nnews3.api.dto.res.MainInfoRes;
import mju.nnews3.api.dto.res.MypageRes;
import mju.nnews3.domain.Member;
import mju.nnews3.execption.KeywordValidationException;
import mju.nnews3.execption.NotFoundUserException;
import mju.nnews3.domain.repository.MemberRepository;
import mju.nnews3.execption.error.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final WeatherService weatherService;

    public MemberService(MemberRepository memberRepository, WeatherService weatherService) {
        this.memberRepository = memberRepository;
        this.weatherService = weatherService;
    }

    public MainInfoRes getMainInfo(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new NotFoundUserException());

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
                throw new KeywordValidationException();
            }
        }

        Member member = memberRepository.findById(keywordReq.id())
                .orElseThrow(NotFoundUserException::new);

        String previousKeyword = keywordReq.previousKeyword();
        String newKeyword = keywordReq.newKeyword();

        if (previousKeyword != null && newKeyword != null) {
            member.updateKeywordList(previousKeyword, newKeyword);
        }

        memberRepository.save(member);
    }

    public MypageRes getMaypage(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new NotFoundUserException());

        return new MypageRes(member.getId(), member.getNickname(), member.getEmail(), member.getKeyword(), member.isAlarm(), member.isLocation());
    }
}
