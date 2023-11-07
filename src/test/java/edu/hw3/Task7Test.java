package edu.hw3;

import java.util.Set;
import java.util.TreeMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task7Test {
    @Test
    @DisplayName("Тест добавления null ключа")
    void nullInTreeMapTest() {
        // given
        TreeMap<String, String> tree = new TreeMap<>(new Task7.NullComparator<>());
        // when
        tree.put(null, "test");
        // then
        assertThat(tree.containsKey(null)).isTrue();
        assertThat(tree.get(null)).isEqualTo("test");
    }

    @Test
    @DisplayName("Проверка сортировки в TreeMap для null")
    void orderInTreeMapTest() {
        // given
        TreeMap<String, String> tree = new TreeMap<>(new Task7.NullComparator<>());
        String[] expected = new String[] {null, "first", "second"};
        // when
        tree.put("second", "testSecond");
        tree.put(null, "test");
        tree.put("first", "testFirst");
        Set<String> keys = tree.keySet();
        // then
        assertThat(tree.containsKey(null)).isTrue();
        assertThat(keys.toArray()).isEqualTo(expected);
    }
}
