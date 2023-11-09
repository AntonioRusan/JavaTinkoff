package edu.hw5;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw5.Task2.getAllFriday13th;
import static edu.hw5.Task2.getNearestFriday13th;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task2Test {

    @Test
    @DisplayName("Проверка всех пятниц 13-е в году")
    void checkAllFriday13th() {
        //given
        int year = 1925;
        List<LocalDate> expected = new ArrayList<>() {{
            add(LocalDate.parse("1925-02-13"));
            add(LocalDate.parse("1925-03-13"));
            add(LocalDate.parse("1925-11-13"));
        }};
        //when
        List<LocalDate> actual = getAllFriday13th(year);
        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Проверка всех пятниц 13-е в году")
    void checkAllFriday13thModern() {
        //given
        int year = 2024;
        List<LocalDate> expected = new ArrayList<>() {{
            add(LocalDate.parse("2024-09-13"));
            add(LocalDate.parse("2024-12-13"));
        }};
        //when
        List<LocalDate> actual = getAllFriday13th(year);
        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Ближайшая пятница 13-e - следующая")
    void checkNextGetNearestFriday13th() {
        //given
        LocalDate input = LocalDate.parse("2024-08-25");
        LocalDate expected = LocalDate.parse("2024-09-13");
        //when
        LocalDate actual = getNearestFriday13th(input);
        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Ближайшая пятница 13-e - предыдущая")
    void checkPreviousGetNearestFriday13th() {
        //given
        LocalDate input = LocalDate.parse("2024-01-01");
        LocalDate expected = LocalDate.parse("2023-10-13");
        //when
        LocalDate actual = getNearestFriday13th(input);
        //then
        assertThat(actual).isEqualTo(expected);
    }

}
