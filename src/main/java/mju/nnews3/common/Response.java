package mju.nnews3.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Response<T>(
        boolean success,
        int status,
        String msg,
        T data
) {
    public static <T> Response<T> success(T data, String msg) {
        return new Response<>(true, HttpStatus.OK.value(), msg, data);
    }

    public static <T> Response<T> success(T data) {
        return success(data, "응답 성공");
    }

    public static <T> Response<T> fail(HttpStatus status, String msg) {
        return new Response<>(false, status.value(), msg, null);
    }
}
