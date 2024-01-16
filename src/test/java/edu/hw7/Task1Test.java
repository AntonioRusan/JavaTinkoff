package edu.hw7;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task1Test {
    @ParameterizedTest
    @CsvSource({
        "2, 100_000",
        "10, 500",
        "100, 100_000",
    })
    @DisplayName("Проверка, что увеличивается общий счётчик")
    void checkSharableValue(int numThreads, int numOfIncrement) {
        //given
        Integer expected = numThreads * numOfIncrement;
        //when
        Integer result = Task1.threadsIncrement(numThreads, numOfIncrement);
        //then
        assertThat(result).isEqualTo(expected);
    }
}
