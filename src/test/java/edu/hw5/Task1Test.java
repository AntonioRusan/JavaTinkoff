package edu.hw5;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task1Test {
    @Test
    @DisplayName("Проверка среднего времени сессии")
    void getAvgDuration() {
        // given
        List<String> sessions = new ArrayList<>() {{
            add("2022-03-13, 20:20 - 2022-03-13, 23:50");
            add("2022-04-01, 21:30 - 2022-04-02, 01:20");
        }};
        String expected = "3ч 40м";
        //when
        String result = Task1.durationToStringHourMinutes(Task1.getAvgDuration(sessions));
        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Отрицательное время")
    void negativeDuration() {
        // given
        List<String> sessions = new ArrayList<>() {{
            add("2022-04-03, 21:30 - 2022-04-02, 01:20");
        }};
        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> Task1.getAvgDuration(sessions));

        String expectedMessage = "Wrong input date!";
        String actualMessage = exception.getMessage();

        // then
        assertThat(expectedMessage).isEqualTo(actualMessage);
    }

    @Test
    @DisplayName("Неправильный входной формат")
    void wrongInputString() {
        // given
        List<String> sessions = new ArrayList<>() {{
            add("2022:03:13, 20:20 - 2022:03:13, 23:50");
        }};
        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> Task1.getAvgDuration(sessions));

        String expectedMessage = "Wrong input date!";
        String actualMessage = exception.getMessage();

        // then
        assertThat(expectedMessage).isEqualTo(actualMessage);
    }

    @Test
    @DisplayName("Неправильный входной формат - одна дата")
    void wrongInputStringOneDate() {
        // given
        List<String> sessions = new ArrayList<>() {{
            add("2022:03:13, 20:20");
        }};
        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> Task1.getAvgDuration(sessions));

        String expectedMessage = "Wrong input date!";
        String actualMessage = exception.getMessage();

        // then
        assertThat(expectedMessage).isEqualTo(actualMessage);
    }

    @Test
    @DisplayName("Проверка если сессия больше 24 часов")
    void testAvgSession() {
        // given
        List<String> sessions = new ArrayList<>() {{
            add("2022-03-10, 20:20 - 2022-03-13, 23:50");
            add("2022-04-01, 21:30 - 2022-04-05, 01:20");
        }};
        String expected = "75ч 40м";
        //when
        String result = Task1.durationToStringHourMinutes(Task1.getAvgDuration(sessions));
        //then
        assertThat(result).isEqualTo(expected);
    }
}
