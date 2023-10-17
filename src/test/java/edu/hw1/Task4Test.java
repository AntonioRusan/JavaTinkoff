package edu.hw1;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task4Test {
    @ParameterizedTest
    @CsvSource(value = {
        "123456; 214365",
        "hTsii  s aimex dpus rtni.g; This is a mixed up string.",
        "badce; abcde",
        "a; a",
        "; ''",
        "''; ''",
        "ab; ba",
    }, delimiterString = ";")
    void countDigitsCsvSourceTest(String inputString, String rightString) {
        // when
        String fixedString = Task4.fixString(inputString);

        // then
        assertThat(fixedString).isEqualTo(rightString);
    }
}
