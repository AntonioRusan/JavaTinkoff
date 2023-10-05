package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task1Test {
    @Test
    @DisplayName("Время с 1 минутой 0 секунд")
    void zeroSecondsTime() {
        // given
        String str = "01:00";

        // when
        int result = Task1.minutesToSeconds(str);

        // then
        assertThat(result).isEqualTo(60);
    }

    @Test
    @DisplayName("Время 0 минут 0 секунд")
    void zeroTime() {
        // given
        String str = "00:00";

        // when
        int result = Task1.minutesToSeconds(str);

        // then
        assertThat(result).isEqualTo(0);
    }

    @Test
    @DisplayName("Обычное время")
    void normalTime() {
        // given
        String str = "13:56";

        // when
        int result = Task1.minutesToSeconds(str);

        // then
        assertThat(result).isEqualTo(836);
    }

    @Test
    @DisplayName("Число секунд >= 60")
    void tooMuchSeconds() {
        // given
        String str = "10:60";

        // when
        int result = Task1.minutesToSeconds(str);

        // then
        assertThat(result).isEqualTo(-1);
    }

    @Test
    @DisplayName("Минуты никак не ограничены")
    void manyMinutes() {
        // given
        String str = "999:59";

        // when
        int result = Task1.minutesToSeconds(str);

        // then
        assertThat(result).isEqualTo(59999);
    }

    @Test
    @DisplayName("Строка не формата mm:ss")
    void notTimeString() {
        // given
        String str = "55:g6";

        // when
        int result = Task1.minutesToSeconds(str);

        // then
        assertThat(result).isEqualTo(-1);
    }
}
