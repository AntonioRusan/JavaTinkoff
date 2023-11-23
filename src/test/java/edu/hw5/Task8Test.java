package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task8Test {
    @ParameterizedTest
    @ValueSource(strings = {"110", "1", "0", "11001"})
    @DisplayName("Строки из алфавита {0, 1} нечетной длины")
    void isCheckOddLength(String input) {
        assertTrue(Task8.checkOddLength(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"10", "11", "00", "11001010", "a1", "3", "1O1"})
    @DisplayName("Строки из алфавита {0, 1} нечетной длины - не выполняется")
    void notCheckOddLength(String input) {
        assertFalse(Task8.checkOddLength(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"011", "10", "0", "000", "1111"})
    @DisplayName(
        "Строка из алфавита {0, 1} начинается с 0 и имеет нечетную длину, или начинается с 1 и имеет четную длину")
    void isCheckOddLengthFromZeroOrEvenLengthFromOne(String input) {
        assertTrue(Task8.checkOddLengthFromZeroOrEvenLengthFromOne(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"101", "0011", "00", "110", "1abc", "03", "O11"})
    @DisplayName(
        "Строка из алфавита {0, 1} начинается с 0 и имеет нечетную длину, или начинается с 1 и имеет четную длину - не выполняется")
    void notCheckOddLengthFromZeroOrEvenLengthFromOne(String input) {
        assertFalse(Task8.checkOddLengthFromZeroOrEvenLengthFromOne(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"000", "101010", "00010001000", "111"})
    @DisplayName("Строка из алфавита {0, 1} количество 0 кратно 3")
    void isNumberOfZeroDivisible3(String input) {
        assertTrue(Task8.numberOfZeroDivisible3(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"101", "0011", "00", "000a", "O1000"})
    @DisplayName("Строка из алфавита {0, 1} количество 0 кратно 3 - не выполняется")
    void notNumberOfZeroDivisible3(String input) {
        assertFalse(Task8.numberOfZeroDivisible3(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"000", "110", "01110", "1111"})
    @DisplayName("Строка из алфавита {0, 1}, кроме 11 или 111")
    void isTwoOrThreeOnes(String input) {
        assertTrue(Task8.notTwoOrThreeOnes(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"11", "111", "a111", "11bc"})
    @DisplayName("Строка из алфавита {0, 1}, кроме 11 или 111 - не выполняется")
    void notTwoOrThreeOnes(String input) {
        assertFalse(Task8.notTwoOrThreeOnes(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "101", "1111", "1010", "1011101"})
    @DisplayName("Строка из алфавита {0, 1}, каждый нечетный символ равен 1")
    void isEveryOddIsOne(String input) {
        assertTrue(Task8.everyOddIsOne(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"110", "0000", "1O1", "10010"})
    @DisplayName("Строка из алфавита {0, 1}, каждый нечетный символ равен 1")
    void notEveryOddIsOne(String input) {
        assertFalse(Task8.everyOddIsOne(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"10", "1", "101", "0101000", "00010101", "0", "000"})
    @DisplayName("Строка из алфавита {0, 1}, нет последовательных 1")
    void noSubsequentOnes(String input) {
        assertTrue(Task8.noSubsequentOnes(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"11", "0110", "1O111", "1000100011", "0l0l", "2"})
    @DisplayName("Строка из алфавита {0, 1}, есть последовательные 1")
    void gotSubsequentOnes(String input) {
        assertFalse(Task8.noSubsequentOnes(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"00", "001", "1000", "01000", "00000", "010"})
    @DisplayName("Строка из алфавита {0, 1}, содержит не менее двух 0 и не более одной 1")
    void moreThan1ZerosLessThan2One(String input) {
        assertTrue(Task8.moreThan1ZerosLessThan2One(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"11", "01", "10", "0", "1", "210", "10", "001001"})
    @DisplayName("Строка из алфавита {0, 1}, содержит не менее двух 0 и не более одной 1 - не выполняется")
    void notMoreThan1ZerosLessThan2One(String input) {
        assertFalse(Task8.moreThan1ZerosLessThan2One(input));
    }
}
