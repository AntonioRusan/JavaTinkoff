package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task7Test {
    @ParameterizedTest
    @ValueSource(strings = {"110", "110110101", "1101", "1100"})
    @DisplayName("Строка содержит не менее 3 символов, причем третий символ равен 0, алфавит {0, 1}")
    void isCheckThirdIsZero(String input) {
        assertTrue(Task7.checkThirdIsZero(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"111", "11", "1102", "110ab91", "g100", "000faa"})
    @DisplayName(
        "Строка не удовлетворяет условию: содержит не менее 3 символов, причем третий символ равен 0, алфавит {0, 1}")
    void notCheckThirdIsZero(String input) {
        assertFalse(Task7.checkThirdIsZero(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"111", "101", "0", "0010100", "10100010111", "1", "11", "00"})
    @DisplayName("Строка начинается и заканчивается одним и тем же символом алфавита {0, 1}")
    void isCheckFirstEqualsLast(String input) {
        assertTrue(Task7.checkFirstEqualsLast(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"10", "01", "121", "1000a1", "01111"})
    @DisplayName("Строка не удовлетворяет условию: начинается и заканчивается одним и тем же символом алфавита {0, 1}")
    void notCheckFirstEqualsLast(String input) {
        assertFalse(Task7.checkFirstEqualsLast(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "0", "101", "00", "000", "111", "10", "01"})
    @DisplayName("Длина не менее 1 и не более 3, алфавит {0, 1}")
    void isCheckLengthFrom1To3(String input) {
        assertTrue(Task7.checkLengthFrom1To3(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "1111", "10001", "1a1", "2", "20", "111B"})
    @DisplayName("Строка не удовлетворяет условию: длина не менее 1 и не более 3, алфавит {0, 1}")
    void notCheckLengthFrom1To3(String input) {
        assertFalse(Task7.checkLengthFrom1To3(input));
    }
}
