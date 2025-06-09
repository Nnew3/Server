package mju.nnews3.domain.view.core.repository;

import mju.nnews3.domain.member.core.Member;
import mju.nnews3.domain.news.core.News;
import mju.nnews3.domain.view.core.View;
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