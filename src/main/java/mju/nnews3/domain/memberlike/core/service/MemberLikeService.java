package mju.nnews3.domain.memberlike.core.service;

import mju.nnews3.domain.member.core.Member;
import mju.nnews3.domain.memberlike.api.dto.LikeReq;
import mju.nnews3.domain.memberlike.core.MemberLike;
import mju.nnews3.domain.news.core.News;
import mju.nnews3.domain.memberlike.core.repository.MemberLikeRepository;
import mju.nnews3.domain.member.core.repository.MemberRepository;
import mju.nnews3.domain.news.core.repository.NewsRepository;
import mju.nnews3.execption.BusinessException;
import mju.nnews3.execption.error.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberLikeService {
    private final MemberRepository memberRepository;
    private final MemberLikeRepository memberLikeRepository;
    private final NewsRepository newsRepository;

    public MemberLikeService(MemberRepository memberRepository, MemberLikeRepository memberLikeRepository, NewsRepository newsRepository) {
        this.memberRepository = memberRepository;
        this.memberLikeRepository = memberLikeRepository;
        this.newsRepository = newsRepository;
    }

    @Transactional
    public void updateLike(LikeReq likeReq) {
        Member member = memberRepository.findById(likeReq.userId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));
        News news = newsRepository.findById(likeReq.newsId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_NEWS));

        MemberLike existingLike = memberLikeRepository.findByMemberAndNews(member, news);

        if (existingLike != null) {
            return;
        } else {
            MemberLike newLike = new MemberLike(null, news, member);
            memberLikeRepository.save(newLike);
        }
    }

    @Transactional
    public void deleteLike(LikeReq likeReq) {
        Member member = memberRepository.findById(likeReq.userId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));
        News news = newsRepository.findById(likeReq.newsId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_NEWS));

        MemberLike existingLike = memberLikeRepository.findByMemberAndNews(member, news);

        if (existingLike == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_NEWS);
        }

        memberLikeRepository.delete(existingLike);
    }
}
