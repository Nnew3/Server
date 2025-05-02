package mju.nnews3.domain.repository;

import mju.nnews3.domain.Member;
import mju.nnews3.domain.News;
import mju.nnews3.domain.View;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViewRepository extends JpaRepository<View, Long> {
    View findByMemberAndNews(Member member, News news);

    @Query("""
    SELECT v.news FROM View v
    WHERE v.member.id = :userId
    ORDER BY v.id DESC
""")
    List<News> findViewedNewsByUserId(@Param("userId") Long userId);

}