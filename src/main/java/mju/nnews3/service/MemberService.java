package mju.nnews3.service;

import mju.nnews3.api.dto.MainInfoRes;
import mju.nnews3.domain.Member;
import mju.nnews3.execption.NotFoundUserException;
import mju.nnews3.domain.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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
}
