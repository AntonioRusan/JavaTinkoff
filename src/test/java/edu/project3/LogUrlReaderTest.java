package edu.project3;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LogUrlReaderTest {
    @Test
    void testLogUrlReader() {
        int expectedSize = 51462;
        // given
        String url =
            "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs";
        //when
        List<LogItem> actual = LogUrlReader.readLogsFromUrl(url).toList();
        //then
        assertThat(actual.size()).isEqualTo(expectedSize);
    }
}
