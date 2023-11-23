package edu.hw5;

import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static edu.hw5.Task3.DateParser;
import static edu.hw5.Task3.getChainOfDateParser;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task3Test {

    @DisplayName("Формат даты через прочерк")
    @ParameterizedTest
    @CsvSource({
        "2020-10-10, 2020-10-10",
        "2020-12-2, 2020-12-02",
        "2020-2-3, 2020-02-03",
        "2020-2-12, 2020-02-12",
    })
    public void checkDashDateParser(String input, String expectedInput) {
        DateParser dateParser = getChainOfDateParser(LocalDate.now());
        Optional<LocalDate> actual = dateParser.parseDate(input);
        Optional<LocalDate> expected = Optional.of(LocalDate.parse(expectedInput));
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("Формат даты через слеш")
    @ParameterizedTest
    @CsvSource({
        "1/3/1976, 1976-03-01",
        "1/3/20, 2020-03-01",
        "1/11/2020, 2020-11-01",
        "10/3/20, 2020-03-10",
    })
    public void checkSlashDateParser(String input, String expectedInput) {
        DateParser dateParser = getChainOfDateParser(LocalDate.now());
        Optional<LocalDate> actual = dateParser.parseDate(input);
        Optional<LocalDate> expected = Optional.of(LocalDate.parse(expectedInput));
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @DisplayName("Дата через слово")
    @CsvSource({
        "today, 0",
        "tomorrow, 1",
        "yesterday, -1"
    })
    public void checkWordDateParser(String input, String offSet) {
        DateParser dateParser = getChainOfDateParser(LocalDate.now());
        Optional<LocalDate> actual = dateParser.parseDate(input);
        Optional<LocalDate> expected = Optional.of(LocalDate.now().plusDays(Integer.parseInt(offSet)));
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @DisplayName("Дата вида: n days ago|later")
    @CsvSource({
        "1 day ago, -1",
        "2234 days ago, -2234",
        "1 day later, 1",
        "133 days later, 133",
    })
    public void checkAgoLaterDateParser(String input, Integer offSet) {
        DateParser dateParser = getChainOfDateParser(LocalDate.now());
        Optional<LocalDate> actual = dateParser.parseDate(input);
        Optional<LocalDate> expected = Optional.of(LocalDate.now().plusDays(offSet));
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @DisplayName("Не поддерживаемый формат")
    @ValueSource(strings = {"2020-22-10", "1 days ago", "2234 day later", "0 days later", "1/33/1976", "1dayago"})
    public void checkAgoLaterDateParser(String input) {
        DateParser dateParser = getChainOfDateParser(LocalDate.now());
        Optional<LocalDate> actual = dateParser.parseDate(input);
        Optional<LocalDate> expected = Optional.empty();
        assertThat(actual).isEqualTo(expected);
    }
}
