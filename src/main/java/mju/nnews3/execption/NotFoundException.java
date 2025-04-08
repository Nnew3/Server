package mju.nnews3.execption;

import mju.nnews3.execption.error.ErrorCode;

public class NotFoundException extends BusinessException {
    public NotFoundException() {
        super(ErrorCode.NOT_FOUND);
    }

    public NotFoundException(String msg) {
        super(ErrorCode.NOT_FOUND);
    }
}
