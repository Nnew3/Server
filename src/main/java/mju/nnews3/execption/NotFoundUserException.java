package mju.nnews3.execption;

import mju.nnews3.execption.error.ErrorCode;

public class NotFoundUserException extends BusinessException {
    public NotFoundUserException() {
        super(ErrorCode.NOT_FOUND_USER);
    }

    public NotFoundUserException(String msg) {
        super(ErrorCode.NOT_FOUND_USER);
    }
}
