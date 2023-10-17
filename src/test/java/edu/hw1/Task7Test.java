package edu.hw1;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task7Test {
    @ParameterizedTest
    @CsvSource(value = {
        "8; 1; 4",
        "8; 33; 4",
        "2147483647; 1; 2147483647",
        "-10; 12; -1"
    }, delimiterString = ";")
    void rotateRightCsvSourceTest(int inputNumber, int inputShift, int expectedNumber) {
        // when
        int shiftResult = Task7.rotateRight(inputNumber, inputShift);

        // then
        assertThat(shiftResult).isEqualTo(expectedNumber);
    }

    @ParameterizedTest
    @CsvSource(value = {
        "16; 1; 1",
        "17; 2; 6",
        "17; 34; 6",
        "-3; 5; -1"
    }, delimiterString = ";")
    void rotateLeftCsvSourceTest(int inputNumber, int inputShift, int expectedNumber) {
        // when
        int shiftResult = Task7.rotateLeft(inputNumber, inputShift);

        // then
        assertThat(shiftResult).isEqualTo(expectedNumber);
    }

}
