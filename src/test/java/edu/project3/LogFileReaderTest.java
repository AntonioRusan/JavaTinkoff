package edu.project3;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LogFileReaderTest {
    @Test
    void testLogFileReader() {
        int expectedSize = 51462;
        // given
        String path = "src/test/resources/project3/logs.txt";
        //when
        List<LogItem> actual = LogFileReader.readLogsFromFile(path).toList();
        //then
        assertThat(actual.size()).isEqualTo(expectedSize);
    }
}
