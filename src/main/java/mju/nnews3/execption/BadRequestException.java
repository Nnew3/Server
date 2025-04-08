package mju.nnews3.execption;

import mju.nnews3.execption.fail.FailCode;
import org.springframework.http.HttpStatus;

public class BadRequestException extends BusinessException {
    public BadRequestException() {
        super(FailCode.INVALID_INPUT);
    }

    public BadRequestException(String msg) {
        super(FailCode.INVALID_INPUT);
    }
}
