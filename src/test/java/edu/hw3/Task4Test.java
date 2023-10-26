package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task4Test {

    @ParameterizedTest
    @CsvSource({
        "2, II",
        "12, XII",
        "16, XVI",
        "3999, MMMCMXCIX",
        "1000, M",
        "888, DCCCLXXXVIII",
        "2001, MMI",
        "9, IX",
        "400, CD",
        "700, DCC"
    })
    @DisplayName("Перевод в римское число")
    void toRoman(int inputNumber, String expectedRoman) {
        // given

        // when
        String actualRoman = Task4.convertToRoman(inputNumber);
        // then
        assertThat(actualRoman).isEqualTo(expectedRoman);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, 99999, 4000})
    @DisplayName("Перевод в римское число")
    void outOfRangeToRoman(int inputNumber) {
        // given

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> Task4.convertToRoman(inputNumber));

        String expectedMessage = "Number out of range!";
        String actualMessage = exception.getMessage();

        // then
        assertThat(expectedMessage).isEqualTo(actualMessage);
    }
}
