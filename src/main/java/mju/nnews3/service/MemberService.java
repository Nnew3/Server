package mju.nnews3.service;

import mju.nnews3.api.dto.MainInfoRes;
import mju.nnews3.domain.Member;
import mju.nnews3.execption.NotFoundException;
import mju.nnews3.domain.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MainInfoRes getMainInfo(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

        String today = LocalDate.now().toString();

        return new MainInfoRes(member.getNickname(), today, "맑음");
    }
}
