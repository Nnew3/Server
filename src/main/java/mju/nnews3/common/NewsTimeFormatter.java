package mju.nnews3.common;

import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class NewsTimeFormatter {

    private final Clock clock;

    public NewsTimeFormatter() {
        this.clock = Clock.systemDefaultZone();
    }

    public String format(LocalDateTime newsDate) {
        LocalDateTime now = LocalDateTime.now(clock);
        Duration duration = Duration.between(newsDate, now);

        if (duration.toDays() >= 1) {
            return duration.toDays() + "일 전";
        }
        if (duration.toHours() >= 1) {
            return duration.toHours() + "시간 전";
        }
        return duration.toMinutes() + "분 전";
    }
}



