package edu.hw3;

import java.util.TreeMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task7Test {
    @Test
    @DisplayName("Тест добавления null ключа")
    void removeStockTest() {
        // given
        TreeMap<String, String> tree = new TreeMap<>(new Task7.NullComparator<>());
        // when
        tree.put(null, "test");
        // then
        assertThat(tree.containsKey(null)).isTrue();
    }
}
