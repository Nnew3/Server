package mju.nnews3.execption;

import mju.nnews3.execption.error.ErrorCode;

public class BusinessException extends RuntimeException{
    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.errorCode = errorCode;
    }

    public ErrorCode getFailCode() {
        return errorCode;
    }
}
