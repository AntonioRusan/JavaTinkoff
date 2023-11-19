package edu.project3;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LogParserTest {
    @Test
    void testLogParser() {
        // given
        LogItem expected = new LogItem(
            "93.180.71.3",
            "-",
            OffsetDateTime.parse("2015-05-17T08:05:32Z", DateTimeFormatter.ISO_OFFSET_DATE_TIME),
            new RequestItem(
                "GET",
                "/downloads/product_1",
                "HTTP/1.1"
            ),
            304,
            0L,
            "-",
            "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
        );
        String log =
            "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"";

        //when
        LogItem actualLog = LogParser.parseLogString(log);

        //then
        assertThat(actualLog).isEqualTo(expected);
    }

    @Test
    void testExceptionLogParser() {
        // given
        String log =
            "93.180.71.3 GET \"/downloads/product_1 HTTP/1.1\" 304 0";

        //when
        assertThrows(RuntimeException.class, () -> LogParser.parseLogString(log));
        //then
    }
}
