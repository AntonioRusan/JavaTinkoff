package edu.project3;

import java.net.URL;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.Locale;

@SuppressWarnings({"RegexpSinglelineJava", "MultipleStringLiterals"})
public class NGINXLogAnalyzer {
    public NGINXLogAnalyzer() {
    }

    public void analyzeLogs(String[] args) {
        try {
            String path = "";
            String from = "";
            String to = "";
            String format = "";

            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("--path")) {
                    path = args[i + 1];
                } else if (args[i].equals("--from")) {
                    from = args[i + 1];
                } else if (args[i].equals("--to")) {
                    to = args[i + 1];
                } else if (args[i].equals("--format")) {
                    format = args[i + 1];
                }
            }
            if (from.isEmpty()) {
                from = "1970-01-01T00:00:00+00:00";
            }
            OffsetDateTime dateFrom = convertDate("1970-01-01T00:00:00+00:00");

            if (to.isEmpty()) {
                LocalDateTime date = LocalDateTime.now();
                ZoneOffset zoneOffset = ZoneOffset.ofHours(0);
                OffsetDateTime timeUtc = date.atOffset(ZoneOffset.UTC);
                OffsetDateTime offsetDate = timeUtc.withOffsetSameInstant(zoneOffset);
                to = offsetDate.toString();
            }
            OffsetDateTime dateTo = convertDate(to);

            List<LogItem> logs = loadFile(path);
            LogReport report = new LogReport(logs, path, dateFrom, dateTo);
            if (format.equalsIgnoreCase("markdown")) {
                System.out.println(report.getMarkdownReport());
            } else if (format.equalsIgnoreCase("adoc")) {
                System.out.println(report.getAdocReport());
            }
        } catch (Exception ex) {
            System.out.println("Failed to analyze logs: " + ex.getMessage());
        }
    }

    private List<LogItem> loadFile(String path) {
        if (isUrlValid(path)) {
            return LogUrlReader.readLogsFromUrl(path).toList();
        } else if (isPathValid(path)) {
            return LogFileReader.readLogsFromFile(path).toList();
        } else {
            throw new IllegalArgumentException("Wrong log source path!");
        }
    }

    private static boolean isUrlValid(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isPathValid(String path) {
        try {
            Paths.get(path);
        } catch (InvalidPathException ex) {
            return false;
        }
        return true;
    }

    public static OffsetDateTime convertDate(String dateString) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        OffsetDateTime inputDate = OffsetDateTime.parse(dateString, inputFormatter);

        DateTimeFormatter outputFormatter = new DateTimeFormatterBuilder()
            .appendPattern("dd/MMM/yyyy:HH:mm:ss Z")
            .toFormatter(Locale.ENGLISH);

        String formattedDateString = inputDate.format(outputFormatter);
        return OffsetDateTime.parse(formattedDateString, outputFormatter);
    }

}
