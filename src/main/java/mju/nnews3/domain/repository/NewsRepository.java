package mju.nnews3.domain.repository;

import mju.nnews3.domain.News;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    Optional<News> findById(Long id);
    Optional<News> findFirstByTitleContainingOrderByDateDesc(String word);

    List<News> findByTitleContainingOrderByDateDesc(String keyword);

    @Query("""
        SELECT n FROM News n
        WHERE n.date BETWEEN :start AND :end
        ORDER BY n.view DESC, n.date DESC
    """)
    List<News> findTopNewsInPeriod(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            Pageable pageable
    );

    Optional<News> findTopByDateBetweenOrderByViewDescDateDesc(Date start, Date end);

    List<News> findByCategory(String category, Sort sort);
}
