package mju.nnews3.domain.mapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum SortType {
    POPULAR("popular", Sort.by(Sort.Direction.DESC, "view")),
    BASIC("basic", Sort.by(Sort.Direction.ASC, "id")),
    RECENT("recent", Sort.by(Sort.Direction.DESC, "date"));

    private final String param;
    private final Sort sort;

    public static SortType fromParam(String param) {
        return Arrays.stream(values())
                .filter(s -> s.param.equalsIgnoreCase(param))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid sort: " + param));
    }
}

