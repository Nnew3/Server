package mju.nnews3.execption;

import mju.nnews3.execption.fail.FailCode;

public class BusinessException extends RuntimeException{
    private final FailCode failCode;

    public BusinessException(FailCode failCode) {
        super(failCode.getMsg());
        this.failCode = failCode;
    }

    public FailCode getFailCode() {
        return failCode;
    }
}
