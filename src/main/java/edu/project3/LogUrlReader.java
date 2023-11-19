package edu.project3;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import static java.time.temporal.ChronoUnit.SECONDS;

public class LogUrlReader {
    private LogUrlReader() {
    }

    private final static long TIMEOUT_DURATION = 10;

    public static List<LogItem> readLogsFromUrl(String url) {
        try {
            var request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .timeout(Duration.of(TIMEOUT_DURATION, SECONDS))
                .build();
            var response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            List<String> bodyAsStringArray = Arrays.asList(responseBody.split("\n"));
            return bodyAsStringArray.stream().map(LogParser::parseLogString).toList();
        } catch (Exception ex) {
            throw new RuntimeException("Error getting data from " + url);
        }
    }
}
