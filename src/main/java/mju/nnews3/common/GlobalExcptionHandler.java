package mju.nnews3.common;

import jakarta.servlet.http.HttpServletRequest;
import mju.nnews3.execption.BusinessException;
import mju.nnews3.execption.NotFoundNewsException;
import mju.nnews3.execption.NotFoundUserException;
import mju.nnews3.execption.error.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExcptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Response<Void>> handleBusinessException(BusinessException ex, HttpServletRequest request) {
        ErrorCode code = ex.getFailCode();
        return ResponseEntity
                .status(code.getHttpStatus())
                .body(Response.fail(code.getHttpStatus(), code.getMsg()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<Void>> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMsg = ex.getBindingResult().getFieldError() != null
                ? ex.getBindingResult().getFieldError().getDefaultMessage()
                : "잘못된 요청입니다.";

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Response.fail(HttpStatus.BAD_REQUEST, errorMsg));
    }

    @ExceptionHandler(NotFoundUserException.class)
    public ResponseEntity<Response<Void>> handleNotFoundUserException(NotFoundUserException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Response.fail(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(NotFoundNewsException.class)
    public ResponseEntity<Response<Void>> handleNotFoundNewsException(NotFoundNewsException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Response.fail(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<Void>> handleUnexpectedException(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Response.fail(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류"));
    }
}
