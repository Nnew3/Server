package mju.nnews3.service;

import mju.nnews3.api.dto.req.LikeReq;
import mju.nnews3.domain.Member;
import mju.nnews3.domain.MemberLike;
import mju.nnews3.domain.News;
import mju.nnews3.domain.repository.MemberLikeRepository;
import mju.nnews3.domain.repository.MemberRepository;
import mju.nnews3.domain.repository.NewsRepository;
import mju.nnews3.execption.NotFoundNewsException;
import mju.nnews3.execption.NotFoundUserException;
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
                .orElseThrow(() -> new NotFoundUserException());
        News news = newsRepository.findById(likeReq.newsId())
                .orElseThrow(() -> new NotFoundNewsException());

        MemberLike existingLike = memberLikeRepository.findByMemberAndNews(member, news);

        if (existingLike != null) {
            return;
        } else {
            MemberLike newLike = new MemberLike(null, news, member);
            memberLikeRepository.save(newLike);
        }
    }

}
