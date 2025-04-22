package mju.nnews3.execption;

import jakarta.servlet.http.HttpServletRequest;
import mju.nnews3.common.Response;
import mju.nnews3.execption.error.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExcptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Response<Void>> handleBusinessException(BusinessException ex) {
        ErrorCode errorCode = ex.getFailCode();
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(Response.fail(errorCode.getHttpStatus(), errorCode.getMsg()));
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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Response<Void>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Response.fail(HttpStatus.BAD_REQUEST, "요청 본문이 잘못되었습니다. 올바른 JSON 형식을 사용하세요."));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Response<Void>> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Response.fail(HttpStatus.NOT_FOUND, "잘못된 URL입니다. 요청한 경로를 찾을 수 없습니다."));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Response<Void>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(Response.fail(HttpStatus.METHOD_NOT_ALLOWED, "지원되지 않는 HTTP 메소드입니다."));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<Void>> handleUnexpectedException(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Response.fail(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류"));
    }
}
