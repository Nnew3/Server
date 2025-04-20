package mju.nnews3.domain.repository;

import mju.nnews3.domain.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    Optional<News> findFirstByTitleContainingOrderByDateDesc(String word);

    List<News> findByTitleContainingOrderByDateDesc(String keyword);
}
