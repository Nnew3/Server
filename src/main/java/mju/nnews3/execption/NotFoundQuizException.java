package mju.nnews3.execption;

import mju.nnews3.execption.error.ErrorCode;

public class NotFoundQuizException extends BusinessException {
    public NotFoundQuizException() {
        super(ErrorCode.NOT_FOUND_QUIZ);
    }

    public NotFoundQuizException(String msg) {
        super(ErrorCode.NOT_FOUND_QUIZ);
    }
}
