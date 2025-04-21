package mju.nnews3.common;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {
    public static String formatDate(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        return localDateTime.format(formatter);
    }

    public static String formatDate(Date date) {
        LocalDateTime localDateTime = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        return formatDate(localDateTime);
    }

    public static LocalDateTime startOfDay(LocalDate date) {
        return date.atStartOfDay();
    }

    public static LocalDateTime endOfDay(LocalDate date) {
        return date.atTime(LocalTime.MAX);
    }

    public static LocalDateTime startOfWeek(LocalDate date) {
        return date.with(DayOfWeek.MONDAY).atStartOfDay();
    }

    public static LocalDateTime endOfWeek(LocalDate date) {
        return date.with(DayOfWeek.SUNDAY).atTime(LocalTime.MAX);
    }

    public static LocalDateTime startOfMonth(LocalDate date) {
        return date.withDayOfMonth(1).atStartOfDay();
    }

    public static LocalDateTime endOfMonth(LocalDate date) {
        return date.withDayOfMonth(date.lengthOfMonth()).atTime(LocalTime.MAX);
    }
}