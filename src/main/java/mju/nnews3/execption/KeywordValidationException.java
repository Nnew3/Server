package mju.nnews3.execption;

import mju.nnews3.execption.error.ErrorCode;

public class KeywordValidationException extends BusinessException {
    public KeywordValidationException() {
        super(ErrorCode.KEYWORD_TOO_LONG_WHITESPACE);
    }

    public KeywordValidationException(String msg) {
        super(ErrorCode.KEYWORD_TOO_LONG_WHITESPACE);
    }
}
