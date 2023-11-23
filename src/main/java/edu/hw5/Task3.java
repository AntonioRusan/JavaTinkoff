package edu.hw5;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task3 {
    private Task3() {
    }

    public static DateParser getChainOfDateParser(LocalDate nowDate) {
        DateParser agoLaterDayDateParser = new AgoLaterDayDateParser(null, nowDate);
        DateParser wordDateParser = new WordDateParser(agoLaterDayDateParser, nowDate);
        DateParser slashDateParser = new SlashDateParser(wordDateParser);
        return new DashDateParser(slashDateParser);
    }

    public abstract static class DateParser {
        public DateParser nextParser;

        public DateParser(DateParser nextParser) {
            this.nextParser = nextParser;
        }

        public abstract Optional<LocalDate> parseDate(String date);
    }

    public static class DashDateParser extends DateParser {
        public DashDateParser(DateParser nextParser) {
            super(nextParser);
        }

        @Override
        public Optional<LocalDate> parseDate(String date) {
            List<DateTimeFormatter> dateFormatterList = new ArrayList<>() {{
                add(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                add(DateTimeFormatter.ofPattern("yyyy-MM-d"));
                add(DateTimeFormatter.ofPattern("yyyy-M-dd"));
                add(DateTimeFormatter.ofPattern("yyyy-M-d"));
            }};
            for (var formatter : dateFormatterList) {
                Optional<LocalDate> parsedDate = parseDateFormat(date, formatter);
                if (parsedDate.isPresent()) {
                    return parsedDate;
                }
            }
            if (nextParser != null) {
                return nextParser.parseDate(date);
            } else {
                return Optional.empty();
            }
        }

        private Optional<LocalDate> parseDateFormat(String date, DateTimeFormatter formatter) {
            try {
                LocalDate parsedDate = LocalDate.parse(date, formatter);
                return Optional.of(parsedDate);
            } catch (Exception ex) {
                return Optional.empty();
            }
        }
    }

    public static class SlashDateParser extends DateParser {
        public SlashDateParser(DateParser nextParser) {
            super(nextParser);
        }

        @Override
        public Optional<LocalDate> parseDate(String date) {
            List<DateTimeFormatter> dateFormatterList = new ArrayList<>() {{
                add(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                add(DateTimeFormatter.ofPattern("dd/MM/yy"));
                add(DateTimeFormatter.ofPattern("dd/M/yyyy"));
                add(DateTimeFormatter.ofPattern("dd/M/yy"));
                add(DateTimeFormatter.ofPattern("d/MM/yyyy"));
                add(DateTimeFormatter.ofPattern("d/MM/yy"));
                add(DateTimeFormatter.ofPattern("d/M/yyyy"));
                add(DateTimeFormatter.ofPattern("d/M/yy"));
            }};
            for (var formatter : dateFormatterList) {
                Optional<LocalDate> parsedDate = parseDateFormat(date, formatter);
                if (parsedDate.isPresent()) {
                    return parsedDate;
                }
            }
            if (nextParser != null) {
                return nextParser.parseDate(date);
            } else {
                return Optional.empty();
            }
        }

        private Optional<LocalDate> parseDateFormat(String date, DateTimeFormatter formatter) {
            try {
                LocalDate parsedDate = LocalDate.parse(date, formatter);
                return Optional.of(parsedDate);
            } catch (Exception ex) {
                return Optional.empty();
            }
        }
    }

    public static class WordDateParser extends DateParser {
        private final LocalDate nowDate;

        public WordDateParser(DateParser nextParser, LocalDate nowDate) {
            super(nextParser);
            this.nowDate = nowDate;
        }

        private static final Map<String, Integer> DAY_TO_OFFSET = new HashMap<>() {{
            put("today", 0);
            put("tomorrow", 1);
            put("yesterday", -1);
        }};

        @Override
        public Optional<LocalDate> parseDate(String date) {
            if (DAY_TO_OFFSET.containsKey(date)) {
                return Optional.of(nowDate.plusDays(DAY_TO_OFFSET.get(date)));
            } else if (nextParser != null) {
                return nextParser.parseDate(date);
            } else {
                return Optional.empty();
            }
        }
    }

    public static class AgoLaterDayDateParser extends DateParser {
        private final LocalDate nowDate;
        public static Pattern pattern = Pattern.compile("^(\\d+) (day|days) (ago|later)$");
        public static final int ONE = 1;
        public static final int TWO = 2;
        public static final int THREE = 3;

        public AgoLaterDayDateParser(DateParser nextParser, LocalDate nowDate) {
            super(nextParser);
            this.nowDate = nowDate;
        }

        @Override
        public Optional<LocalDate> parseDate(String date) {
            Matcher matcher = pattern.matcher(date);
            if (matcher.matches()) {
                Long number = Long.valueOf(matcher.group(ONE));
                String dayWord = matcher.group(TWO);
                String direction = matcher.group(THREE);
                if ((number == 1 && dayWord.equals("days")) || (number > 1 && dayWord.equals("day")) || (number == 0)) {
                    return Optional.empty();
                } else {
                    if (direction.equals("ago")) {
                        number *= -1;
                    }
                    return Optional.of(nowDate.plusDays(number));
                }
            } else if (nextParser != null) {
                return nextParser.parseDate(date);
            } else {
                return Optional.empty();
            }
        }
    }
}
