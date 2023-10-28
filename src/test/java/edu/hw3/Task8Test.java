package edu.hw3;

import edu.hw3.Task8.BackwardIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task8Test {

    @Test
    @DisplayName("Проход по обратному итератору")
    void backwardIteratorTest() {
        // given
        BackwardIterator<Integer> backIter = new BackwardIterator<>(List.of(1, 2, 3));
        List<Integer> expectedResult = List.of(3, 2, 1);
        List<Integer> actualResult = new ArrayList<>();
        // when
        // then
        while (backIter.hasNext()) {
            actualResult.add(backIter.next());
        }
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("Если нет предыдущего элемента, то next() возвращает исключение")
    void noNextElementIteratorTest() {
        // given
        BackwardIterator<Integer> backIter = new BackwardIterator<>(List.of(1));
        // when
        backIter.next();
        // then
        assertThrows(NoSuchElementException.class, () -> backIter.next());
    }

    @Test
    @DisplayName("Проверка hasNext() и next()")
    void hasNextIteratorTest() {
        // given
        ArrayList<String> test = new ArrayList<>();
        String expected = "test";
        test.add(expected);
        BackwardIterator<String> backIter = new BackwardIterator<>(test);
        assertThat(backIter.hasNext()).isTrue();

        // when
        String result = backIter.next();
        assertThat(result).isEqualTo(expected);
        assertThat(backIter.hasNext()).isFalse();

        // then
        assertThat(backIter.hasNext()).isFalse();
    }
}
