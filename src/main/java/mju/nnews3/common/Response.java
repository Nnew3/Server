package mju.nnews3.common;

import org.springframework.http.HttpStatus;

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
        return success(data, "ok");
    }

    public static <T> Response<T> fail(HttpStatus status, String msg) {
        return new Response<>(false, status.value(), msg, null);
    }
}
