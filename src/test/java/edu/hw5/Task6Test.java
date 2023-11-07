package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task6Test {
    @ParameterizedTest
    @CsvSource(value = {
        "abcd; awxbdcpqd",
        "abc; achfdbaabgabcaabg",
        "*^|; abc*^gg|l",
        "$$$; a$c*$^gg|l$",
        "abcd; abgbqcd",
    }, delimiterString = ";")
    @DisplayName("Строка является подпоследовательностью другой строки")
    void isSubsequenceTest(String subString, String mainString) {
        assertTrue(Task6.isSubsequence(subString, mainString));
    }
    @ParameterizedTest
    @CsvSource(value = {
        "abcd; awxbdcpqh",
        "abc; abbbbbbdс",
        "*^|; abc*^ggl",
        "abcd; 1",
    }, delimiterString = ";")
    @DisplayName("Строка не является подпоследовательностью другой строки")
    void notSubsequenceTest(String subString, String mainString) {
        assertFalse(Task6.isSubsequence(subString, mainString));
    }
}

