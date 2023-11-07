package edu.hw3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task3Test {

    @Test
    @DisplayName("Список строк")
    void listOfStringFreqDictTest() {
        // given
        ArrayList<String> inputArray = new ArrayList<>(Arrays.asList("a", "bb", "a", "bb"));
        HashMap<String, Integer> expectedDict = new HashMap<>() {{
            put("bb", 2);
            put("a", 2);
        }};
        // when
        HashMap<String, Integer> dict = Task3.frequencyDict(inputArray);
        // then
        assertThat(dict).isEqualTo(expectedDict);
    }

    @Test
    @DisplayName("Список побольше строк")
    void listOfManyStringFreqDictTest() {
        // given
        ArrayList<String> inputArray = new ArrayList<>(Arrays.asList("this", "and", "that", "and"));
        HashMap<String, Integer> expectedDict = new HashMap<>() {{
            put("that", 1);
            put("and", 2);
            put("this", 1);
        }};
        // when
        HashMap<String, Integer> dict = Task3.frequencyDict(inputArray);
        // then
        assertThat(dict).isEqualTo(expectedDict);
    }

    @Test
    @DisplayName("Список русских и английских строк")
    void listOfRussianEnglishStringFreqDictTest() {
        // given
        ArrayList<String> inputArray = new ArrayList<>(Arrays.asList("код", "код", "код", "bug"));
        HashMap<String, Integer> expectedDict = new HashMap<>() {{
            put("код", 3);
            put("bug", 1);
        }};
        // when
        HashMap<String, Integer> dict = Task3.frequencyDict(inputArray);
        // then
        assertThat(dict).isEqualTo(expectedDict);
    }

    @Test
    @DisplayName("Список целых чисел")
    void listOfIntegerFreqDictTest() {
        // given
        ArrayList<Integer> inputArray = new ArrayList<>(Arrays.asList(1, 1, 2, 2));
        HashMap<Integer, Integer> expectedDict = new HashMap<>() {{
            put(1, 2);
            put(2, 2);
        }};
        // when
        HashMap<Integer, Integer> dict = Task3.frequencyDict(inputArray);
        // then
        assertThat(dict).isEqualTo(expectedDict);
    }
}
