package edu.hw1;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task5Test {
    @ParameterizedTest
    @ValueSource(ints = {11211230, 13001120, 23336014, 11, 111, 5, 11211, 112})
    void isPalindromeDescendantTest(Integer inputNumber) {
        // when
        boolean result = Task5.isPalindromeDescendant(inputNumber);

        // then
        assertThat(result).isTrue();
    }

    @ParameterizedTest
    @ValueSource(ints = {1112, 1324, 12, 125})
    void notPalindromeDescendantTest(Integer inputNumber) {
        // when
        boolean result = Task5.isPalindromeDescendant(inputNumber);

        // then
        assertThat(result).isFalse();
    }
}
