package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task5Test {
    @ParameterizedTest
    @ValueSource(strings = {"A123BE777", "O777OO177", "C065MK78", "X111YT101", "X111YT01"})
    @DisplayName("Правильный номерной знак")
    void plateIsValidTest(String password) {
        assertTrue(Task5.checkRussianLicensePlate(password));
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "C065MK001", "", "Б065ЛK777", "R065MK781"})
    @DisplayName("Неправильный номерной знак")
    void plateNotValidTest(String password) {
        assertFalse(Task5.checkRussianLicensePlate(password));
    }
}
