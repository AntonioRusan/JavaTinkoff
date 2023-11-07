package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task4Test {
    @ParameterizedTest
    @ValueSource(strings = {"!", "abc^!", "|12av", "***", "ab@g%j$", "$12"})
    @DisplayName("Пароль содержит хотя бы один нужный символ")
    void passwordIsValidTest(String password) {
        assertTrue(Task4.checkPassword(password));
    }

    @ParameterizedTest
    @ValueSource(strings = {"234", "abc", "", "gg", "12af4", "b+12"})
    @DisplayName("Пароль не содержит нужный символ")
    void passwordNotValidTest(String password) {
        assertFalse(Task4.checkPassword(password));
    }
}
