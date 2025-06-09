package mju.nnews3.domain.quiz.api.dto.res;

import mju.nnews3.domain.quiz.api.dto.QuizAnswerDto;

import java.util.List;

public record QuizRes(
        String question,
        List<QuizAnswerDto> optionList,
        Integer answer,
        Integer score
) {
}
