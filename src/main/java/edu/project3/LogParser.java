package edu.project3;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("MagicNumber")
public class LogParser {
    private LogParser() {
    }

    private static final String LOG_PATTERN =
        "^(\\S+) - (\\S+) \\[(\\d{2}/\\w+/\\d{4}:\\d{2}:\\d{2}:\\d{2} "
            + "\\+\\d{4})\\] \"((\\S+)\\s*(\\S*)\\s*(\\S*))\" (\\d{3}) (\\d+) \"([^\"]*)\" \"([^\"]*)\"$";

    public static LogItem parseLogString(String logLine) {
        DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
            .appendPattern("dd/MMM/yyyy:HH:mm:ss Z")
            .toFormatter(Locale.ENGLISH);
        Pattern pattern = Pattern.compile(LOG_PATTERN);
        Matcher matcher = pattern.matcher(logLine);
        try {
            if (matcher.matches()) {
                String remoteAddr = matcher.group(1);
                String remoteUser = matcher.group(2);
                String timeString = matcher.group(3);
                OffsetDateTime timeLocal = OffsetDateTime.parse(timeString, dateTimeFormatter);
                RequestItem request = new RequestItem(matcher.group(5), matcher.group(6), matcher.group(7));
                int status = Integer.parseInt(matcher.group(8));
                long bodyBytesSent = Integer.parseInt(matcher.group(9));
                String httpReferer = matcher.group(10);
                String httpUserAgent = matcher.group(11);

                return new LogItem(
                    remoteAddr,
                    remoteUser,
                    timeLocal,
                    request,
                    status,
                    bodyBytesSent,
                    httpReferer,
                    httpUserAgent
                );
            } else {
                throw new RuntimeException();
            }
        } catch (Exception ex) {
            throw new RuntimeException("Error while parsing log: " + ex.getMessage());
        }

    }
}
