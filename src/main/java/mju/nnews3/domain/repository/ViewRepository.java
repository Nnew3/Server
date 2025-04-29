package mju.nnews3.domain.repository;

import mju.nnews3.domain.Member;
import mju.nnews3.domain.News;
import mju.nnews3.domain.View;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewRepository extends JpaRepository<View, Long> {
    View findByMemberAndNews(Member member, News news);

}
