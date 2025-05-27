package mju.nnews3.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import mju.nnews3.domain.Member;
import mju.nnews3.domain.News;
import mju.nnews3.domain.repository.MemberRepository;
import mju.nnews3.domain.repository.NewsRepository;
import mju.nnews3.execption.BusinessException;
import mju.nnews3.execption.error.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FCMService {
    private final MemberRepository memberRepository;
    private final NewsRepository newsRepository;

    public FCMService(MemberRepository memberRepository, NewsRepository newsRepository) {
        this.memberRepository = memberRepository;
        this.newsRepository = newsRepository;
    }

    @Transactional
    public void registerToken(Long id, String token) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_FCM_TOKEN));

        member.updateToken(token);
    }

    @Transactional
    public void sendBreakingNews(String title) {
        List<Member> members = memberRepository.findAll();

        for (Member member : members) {
            String token = member.getToken();
            if (token == null || token.isBlank()) continue;

            Message message = Message.builder()
                    .setToken(token)
                    .setNotification(Notification.builder()
                            .setTitle("속보")
                            .setBody(title)
                            .build())
                    .build();

            try {
                FirebaseMessaging.getInstance().send(message);
            } catch (FirebaseMessagingException e) {
                System.err.println("FCM 전송 실패: " + e.getMessage());
            }
        }
    }
}
