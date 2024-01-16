package edu.hw7;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task2Test {

    @ParameterizedTest
    @CsvSource({
        "1, 1",
        "0, 1",
        "5, 120",
        "10, 3628800",
        "-1, -1",
    })
    @DisplayName("Проверка многопоточного факторила с parllelStream")
    void checkGetStreamParallelFactorial(int number, long expectedFactorial) {
        //given
        //when
        Long result = Task2.getStreamParallelFactorial(number);
        //then
        assertThat(result).isEqualTo(expectedFactorial);
    }
}
