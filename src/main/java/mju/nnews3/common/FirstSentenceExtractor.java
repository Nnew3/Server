package mju.nnews3.common;

import org.springframework.stereotype.Component;

@Component
public class FirstSentenceExtractor {

    public String extract(String summary) {
        if (summary == null || summary.isBlank()) return "";

        int end = summary.indexOf(".");
        return (end != -1) ? summary.substring(0, end + 1) : summary;
    }
}
