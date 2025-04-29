package mju.nnews3.domain.repository;

import mju.nnews3.domain.MemberLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mju.nnews3.domain.Member;
import mju.nnews3.domain.News;

import java.util.List;

@Repository
public interface MemberLikeRepository extends JpaRepository<MemberLike, Long> {
    MemberLike findByMemberAndNews(Member member, News news);

    @Query("""
    SELECT v.news FROM View v
    WHERE v.member.id = :userId
    ORDER BY v.id DESC
""")
    List<News> findViewedNewsByUserId(@Param("userId") Long userId);
}
