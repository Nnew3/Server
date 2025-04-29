package mju.nnews3.service;

import mju.nnews3.api.dto.req.ViewReq;
import mju.nnews3.domain.Member;
import mju.nnews3.domain.News;
import mju.nnews3.domain.View;
import mju.nnews3.domain.repository.MemberRepository;
import mju.nnews3.domain.repository.NewsRepository;
import mju.nnews3.domain.repository.ViewRepository;
import mju.nnews3.execption.NotFoundNewsException;
import mju.nnews3.execption.NotFoundUserException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ViewService {
    private final ViewRepository viewRepository;
    private final NewsRepository newsRepository;
    private final MemberRepository memberRepository;

    public ViewService(ViewRepository viewRepository, NewsRepository newsRepository, MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
        this.viewRepository = viewRepository;
        this.newsRepository = newsRepository;
    }

    @Transactional
    public void updateView(ViewReq viewReq) {
        Member member = memberRepository.findById(viewReq.userId())
                .orElseThrow(() -> new NotFoundUserException());

        News news = newsRepository.findById(viewReq.newsId())
                .orElseThrow(() -> new NotFoundNewsException());

        View existingView = viewRepository.findByMemberAndNews(member, news);

        if (existingView != null) {
            return;
        } else {
            View view = new View(null, news, member);
            viewRepository.save(view);

            news.updateView();

            newsRepository.save(news);
        }
    }
}
