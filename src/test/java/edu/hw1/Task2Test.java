package edu.hw1;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task2Test {

    @ParameterizedTest
    @CsvSource({
        "4666, 4",
        "544, 3",
        "0, 1",
        "99999, 5",
        "1000, 4",
        "-195, 3",
        "-5, 1"
    })
    void countDigitsCsvSourceTest(int inputNumber, int expectedDigitCount) {
        // when
        int digitCount = Task2.countDigits(inputNumber);

        // then
        assertThat(digitCount).isEqualTo(expectedDigitCount);
    }
}
