package mju.nnews3.execption;

import mju.nnews3.execption.error.ErrorCode;

public class DuplicateKeywordException extends BusinessException {
    public DuplicateKeywordException() {
        super(ErrorCode.DUPLICATE_KEYWORD);
    }

    public DuplicateKeywordException(String msg) {
        super(ErrorCode.DUPLICATE_KEYWORD);
    }
}
