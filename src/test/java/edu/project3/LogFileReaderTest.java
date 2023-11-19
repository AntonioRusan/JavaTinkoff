package edu.project3;

import org.junit.jupiter.api.Test;

public class LogFileReaderTest {
    @Test
    void testLogUrlReader() {
        // given
        String path = "src/test/resources/project3/logs.txt";
        var gg = LogFileReader.readLogsFromFile(path);
        var f = 1;
        //when

        //then
    }
}
