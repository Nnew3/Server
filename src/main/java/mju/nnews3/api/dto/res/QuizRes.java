package mju.nnews3.api.dto.res;

import java.util.List;

public record QuizRes(
        String question,
        List<QuizAnswerDto> optionList,
        Integer answer,
        Integer score
) {
}
