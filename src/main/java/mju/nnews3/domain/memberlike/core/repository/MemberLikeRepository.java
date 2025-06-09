package mju.nnews3.domain.memberlike.core.repository;

import mju.nnews3.domain.memberlike.core.MemberLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mju.nnews3.domain.member.core.Member;
import mju.nnews3.domain.news.core.News;

import java.util.List;

@Repository
public interface MemberLikeRepository extends JpaRepository<MemberLike, Long> {
    MemberLike findByMemberAndNews(Member member, News news);

    @Query("""
    SELECT ml.news FROM MemberLike ml
    WHERE ml.member.id = :userId
    ORDER BY ml.id DESC
""")
    List<News> findLikedNewsByUserId(@Param("userId") Long userId);

}
