package mju.nnews3.domain.quiz.api.controller;

import mju.nnews3.common.Response;
import mju.nnews3.domain.quiz.api.dto.res.QuizRes;
import mju.nnews3.domain.quiz.core.Quiz;
import mju.nnews3.domain.quiz.core.service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuizController {
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/user/v1/quiz")
    public ResponseEntity<Response<QuizRes>> getQuiz(@RequestParam Integer num) {
        Quiz quiz = quizService.getRandomQuiz(num);
        QuizRes quizRes = quizService.getQuiz(quiz.getId());

        return ResponseEntity.ok(Response.success(quizRes));
    }
}
