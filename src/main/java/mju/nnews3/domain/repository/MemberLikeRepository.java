package mju.nnews3.domain.repository;

import mju.nnews3.domain.MemberLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mju.nnews3.domain.Member;
import mju.nnews3.domain.News;

@Repository
public interface MemberLikeRepository extends JpaRepository<MemberLike, Long> {
    MemberLike findByMemberAndNews(Member member, News news);
}
