package mju.nnews3.domain.mapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoryType {
    POLITICS("politics", "politics"),
    ECONOMIC("economic", "economic"),
    REGION("region", "region"),
    CULTURE("culture", "culture"),
    SCIENCE("science", "science"),
    ENTERTAINMENT("entertainment", "entertainment"),
    SPORTS("sports", "sports"),
    KEYWORD("keyword", "keyword");

    private final String param;
    private final String display;

    public static CategoryType fromParam(String param) {
        for (CategoryType category : values()) {
            if (category.getParam().equalsIgnoreCase(param)) {
                return category;
            }
        }
        throw new IllegalArgumentException("존재하지 않는 카테고리: " + param);
    }
}


