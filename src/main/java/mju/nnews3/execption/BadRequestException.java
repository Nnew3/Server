package mju.nnews3.execption;

import mju.nnews3.execption.error.ErrorCode;

public class BadRequestException extends BusinessException {
    public BadRequestException() {
        super(ErrorCode.INVALID_INPUT);
    }

    public BadRequestException(String msg) {
        super(ErrorCode.INVALID_INPUT);
    }
}
