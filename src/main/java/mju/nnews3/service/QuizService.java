package mju.nnews3.service;

import mju.nnews3.api.dto.res.QuizAnswerDto;
import mju.nnews3.api.dto.res.QuizRes;
import mju.nnews3.domain.Quiz;
import mju.nnews3.domain.repository.QuizRepository;
import mju.nnews3.execption.NotFoundQuizException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class QuizService {
    private final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public Quiz getRandomQuiz(Integer num) {
        List<Quiz> quizList = quizRepository.findByNum(num);

        if (quizList.isEmpty()) {
            throw new NotFoundQuizException();
        }

        Random random = new Random();

        return quizList.get(random.nextInt(quizList.size()));
    }

    public QuizRes getQuiz(Long id) {
        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new NotFoundQuizException());

        List<QuizAnswerDto> optionList = List.of(
                new QuizAnswerDto(1, quiz.getOption1()),
                new QuizAnswerDto(2, quiz.getOption2()),
                new QuizAnswerDto(3, quiz.getOption3()),
                new QuizAnswerDto(4, quiz.getOption4())
        );

        Integer score = quiz.getNum() == 0 ? 50 : quiz.getNum() * 10;

        return new QuizRes(
                quiz.getQuestion(),
                optionList,
                quiz.getAnswer(),
                score
        );
    }
}
