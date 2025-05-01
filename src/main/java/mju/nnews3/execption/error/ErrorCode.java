package mju.nnews3.execption.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류가 발생했습니다."),
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    NOT_FOUND_NEWS(HttpStatus.NOT_FOUND, "존재하지 않는 뉴스입니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),
    NOT_FOUND_QUIZ(HttpStatus.NOT_FOUND, "존재하지 않는 퀴즈입니다."),

    INVALID_KEYWORD(HttpStatus.BAD_REQUEST,"잘못된 키워드입니다."),
    DUPLICATE_KEYWORD(HttpStatus.BAD_REQUEST, "이미 존재하는 키워드입니다."),
    KEYWORD_TOO_LONG_WHITESPACE(HttpStatus.BAD_REQUEST,"키워드는 공백을 포함할 수 없으며, 10자 이상일 수 없습니다."),;

    private final HttpStatus httpStatus;
    private final String msg;
}
