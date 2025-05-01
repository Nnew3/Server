package mju.nnews3.execption;

import mju.nnews3.execption.error.ErrorCode;

public class NotFoundKeywordException extends BusinessException {
    public NotFoundKeywordException() {
        super(ErrorCode.NOT_FOUND_NEWS);
    }

    public NotFoundKeywordException(String msg) {
        super(ErrorCode.NOT_FOUND_NEWS);
    }
}

