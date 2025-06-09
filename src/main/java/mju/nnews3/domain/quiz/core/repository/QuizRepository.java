package mju.nnews3.domain.quiz.core.repository;

import mju.nnews3.domain.quiz.core.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByNum(Integer num);
}
