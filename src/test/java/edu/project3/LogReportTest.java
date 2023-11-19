package edu.project3;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LogReportTest {
    @Test
    void testLogReportWithoutDates() {
        // given
        int expected = 51462;
        String path = "src/test/resources/project3/logs.txt";
        List<LogItem> logs = LogFileReader.readLogsFromFile(path);
        OffsetDateTime dateFrom = NGINXLogAnalyzer.convertDate("1970-01-01T00:00:00+00:00");
        LocalDateTime date = LocalDateTime.now();
        ZoneOffset zoneOffset = ZoneOffset.ofHours(0);
        OffsetDateTime timeUtc = date.atOffset(ZoneOffset.UTC);
        OffsetDateTime offsetDate = timeUtc.withOffsetSameInstant(zoneOffset);
        OffsetDateTime dateTo = NGINXLogAnalyzer.convertDate(offsetDate.toString());
        //when
        LogReport report = new LogReport(logs, path, dateFrom, dateTo);
        //then
        assertThat(report.totalRequests).isEqualTo(expected);
    }

    @Test
    void testLogReportWithDates() {
        // given
        int expected = 3;
        String path = "src/test/resources/project3/mini_logs.txt";
        List<LogItem> logs = LogFileReader.readLogsFromFile(path);
        OffsetDateTime dateFrom = NGINXLogAnalyzer.convertDate("2015-05-17T10:05:20+01:00");
        OffsetDateTime dateTo = NGINXLogAnalyzer.convertDate("2015-05-24T08:05:32+01:00");
        //when
        LogReport report = new LogReport(logs, path, dateFrom, dateTo);
        //then
        assertThat(report.totalRequests).isEqualTo(expected);
    }
}
