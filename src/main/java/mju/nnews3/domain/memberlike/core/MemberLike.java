package mju.nnews3.domain.memberlike.core;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mju.nnews3.domain.member.core.Member;
import mju.nnews3.domain.news.core.News;

@Entity
@Table(name = "member_like")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id")
    private News news;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
