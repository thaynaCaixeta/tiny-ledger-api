package com.tackr.tinyledger.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static final String pattern = "dd-MM-yyyy HH:mm:ss";

    public static String toCustomFormat(LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDateTime toLocalDateTime(String timestamp) {
        return LocalDateTime.parse(timestamp, DateTimeFormatter.ofPattern(pattern));
    }
}
