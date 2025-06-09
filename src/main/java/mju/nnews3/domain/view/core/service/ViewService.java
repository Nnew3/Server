package mju.nnews3.domain.view.core.service;

import mju.nnews3.domain.view.api.dto.ViewReq;
import mju.nnews3.domain.member.core.Member;
import mju.nnews3.domain.news.core.News;
import mju.nnews3.domain.member.core.repository.MemberRepository;
import mju.nnews3.domain.news.core.repository.NewsRepository;
import mju.nnews3.domain.view.core.View;
import mju.nnews3.domain.view.core.repository.ViewRepository;
import mju.nnews3.execption.BusinessException;
import mju.nnews3.execption.error.ErrorCode;
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
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));

        News news = newsRepository.findById(viewReq.newsId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_NEWS));

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
