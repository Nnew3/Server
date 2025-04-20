package mju.nnews3.execption;

import mju.nnews3.execption.error.ErrorCode;

public class NotFoundNewsException extends BusinessException {
    public NotFoundNewsException() {
        super(ErrorCode.NOT_FOUND_NEWS);
    }

    public NotFoundNewsException(String msg) {
        super(ErrorCode.NOT_FOUND_NEWS);
    }
}

