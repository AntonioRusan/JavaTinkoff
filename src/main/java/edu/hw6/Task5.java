package edu.hw6;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static java.time.temporal.ChronoUnit.SECONDS;

public class Task5 {

    public static class HackerNews {
        private final static Pattern JSON_TITLE_PATTERN = Pattern.compile("\"(title)\":\"([^\"]+)\"");
        private final static Logger LOGGER = LogManager.getLogger();

        private final static String HEADER_NAME = "Accept-Encoding";
        private final static String HEADER_VALUE = "gzip";
        private final static long TIMEOUT_DURATION = 10;

        public HackerNews() {

        }

        public static long[] hackerNewsTopStories() {
            try {
                var request = HttpRequest.newBuilder()
                    .uri(new URI("https://hacker-news.firebaseio.com/v0/topstories.json"))
                    .GET()
                    .header(HEADER_NAME, HEADER_VALUE)
                    .timeout(Duration.of(TIMEOUT_DURATION, SECONDS))
                    .build();
                var response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
                String responseBody = response.body();
                String[] bodyAsStringArray = responseBody.replace("[", "").replace("]", "").split(",");
                long[] storyIds = new long[bodyAsStringArray.length];
                for (int i = 0; i < bodyAsStringArray.length; i++) {
                    storyIds[i] = Long.parseLong(bodyAsStringArray[i]);
                }
                return storyIds;
            } catch (Exception e) {
                LOGGER.info("Problem getting top stories from hacker-news");
                return new long[0];
            }
        }

        public static String getNewsTitle(long id) {
            try {
                var request = HttpRequest.newBuilder()
                    .uri(new URI(String.format("https://hacker-news.firebaseio.com/v0/item/%d.json", id)))
                    .GET()
                    .header(HEADER_NAME, HEADER_VALUE)
                    .timeout(Duration.of(TIMEOUT_DURATION, SECONDS))
                    .build();
                var response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
                String responseBody = response.body();
                Matcher matcher = JSON_TITLE_PATTERN.matcher(responseBody);
                if (matcher.find()) {
                    return matcher.group(2);
                } else {
                    throw new RuntimeException();
                }
            } catch (Exception e) {
                throw new RuntimeException(String.format("Problem getting news %d from hacker-news", id));
            }
        }
    }
}
