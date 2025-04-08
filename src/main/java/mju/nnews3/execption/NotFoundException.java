package mju.nnews3.execption;

import mju.nnews3.execption.fail.FailCode;
import org.springframework.http.HttpStatus;

public class NotFoundException extends BusinessException {
    public NotFoundException() {
        super(FailCode.NOT_FOUND);
    }

    public NotFoundException(String msg) {
        super(FailCode.NOT_FOUND);
    }
}
