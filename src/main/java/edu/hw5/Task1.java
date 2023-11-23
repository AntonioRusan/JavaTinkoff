package edu.hw5;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Task1 {
    private Task1() {
    }

    public static long getDuration(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm");
        String[] dates = input.split(" - ");
        try {
            if (dates.length == 2) {
                LocalDateTime begin = LocalDateTime.parse(dates[0].strip(), formatter);
                LocalDateTime end = LocalDateTime.parse(dates[1].strip(), formatter);
                Duration timeDuration = Duration.between(begin, end);
                if (timeDuration.isNegative()) {
                    throw new IllegalArgumentException();
                } else {
                    return timeDuration.toMillis();
                }
            } else {
                throw new IllegalArgumentException();
            }
        } catch (Exception ex) {
            throw new IllegalArgumentException("Wrong input date!");
        }
    }

    public static Duration getAvgDuration(List<String> sessions) {
        Double avgTimeInMillis = sessions.stream().mapToLong(Task1::getDuration).average().orElseThrow();
        Duration avgDuration = Duration.ofMillis(avgTimeInMillis.longValue());
        return avgDuration;

    }

    public static String durationToStringHourMinutes(Duration dur) {
        return String.format("%dч %dм", dur.toHours(), dur.toMinutesPart());
    }

}
