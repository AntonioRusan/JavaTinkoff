package edu.project3;

import org.junit.jupiter.api.Test;

public class LogUrlReaderTest {
    @Test
    void testLogUrlReader() {
        // given
        String url =
            "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs";
        var gg = LogUrlReader.readLogsFromUrl(url);
        var f = 1;
        //when

        //then
    }
}
