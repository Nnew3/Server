package mju.nnews3.domain.quiz.core.service;

import mju.nnews3.domain.quiz.api.dto.QuizAnswerDto;
import mju.nnews3.domain.quiz.api.dto.res.QuizRes;
import mju.nnews3.domain.quiz.core.Quiz;
import mju.nnews3.domain.quiz.core.repository.QuizRepository;
import mju.nnews3.execption.BusinessException;
import mju.nnews3.execption.error.ErrorCode;
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
            throw new BusinessException(ErrorCode.NOT_FOUND_QUIZ);
        }

        Random random = new Random();

        return quizList.get(random.nextInt(quizList.size()));
    }

    public QuizRes getQuiz(Long id) {
        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_QUIZ));

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
