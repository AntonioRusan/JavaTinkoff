package edu.hw1;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task6Test {
    @ParameterizedTest
    @CsvSource(value = {
        "6174; 0",
        "6621; 5",
        "6554; 4",
        "1234; 3",
        "1001; 4",
        "3524; 3",
        "1000; -1",
        "99999; -1",
        "999; -1",
    }, delimiterString = ";")
    void countKaprekarCsvSourceTest(int inputNumber, int expectedCount) {
        // when
        int count = Task6.countKaprekar(inputNumber);

        // then
        assertThat(count).isEqualTo(expectedCount);
    }
}
