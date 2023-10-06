package edu.hw1;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task1Test {
    @ParameterizedTest
    @CsvSource({
        "01:00, 60",
        "00:00, 0",
        "13:56, 836",
        "10:60, -1",
        "999:59, 59999",
        "55:g6, -1",
        "100.12, -1",
        "20:-1, -1"

    })
    void minutesToSecondsCsvSourceTest(String timeLength, int expectedTotalTime) {
        // when
        int totalTimeInSeconds = Task1.minutesToSeconds(timeLength);

        // then
        assertThat(totalTimeInSeconds).isEqualTo(expectedTotalTime);
    }
}
