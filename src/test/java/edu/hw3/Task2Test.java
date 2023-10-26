package edu.hw3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task2Test {

    @Test
    @DisplayName("Набор маленьких кластеров")
    void simpleManyClustersTest() {
        // given
        String inputString = "()()()";
        ArrayList<String> expectedClusters = new ArrayList<>(Arrays.asList("()", "()", "()"));
        // when
        ArrayList<String> clusters = Task2.clusterizeBrackets(inputString);
        // then
        assertThat(clusters).isEqualTo(expectedClusters);
    }

    @Test
    @DisplayName("Один большой кластер")
    void oneBigClusterTest() {
        // given
        String inputString = "((()))";
        ArrayList<String> expectedClusters = new ArrayList<>(List.of("((()))"));
        // when
        ArrayList<String> clusters = Task2.clusterizeBrackets(inputString);
        // then
        assertThat(clusters).isEqualTo(expectedClusters);
    }

    @Test
    @DisplayName("Разные кластеры")
    void differentClustersTest() {
        // given
        String inputString = "((()))(())()()(()())";
        ArrayList<String> expectedClusters = new ArrayList<>(Arrays.asList("((()))", "(())", "()", "()", "(()())"));
        // when
        ArrayList<String> clusters = Task2.clusterizeBrackets(inputString);
        // then
        assertThat(clusters).isEqualTo(expectedClusters);
    }

    @Test
    @DisplayName("Два больших кластера")
    void twoBigClustersTest() {
        // given
        String inputString = "((())())(()(()()))";
        ArrayList<String> expectedClusters = new ArrayList<>(Arrays.asList("((())())", "(()(()()))"));
        // when
        ArrayList<String> clusters = Task2.clusterizeBrackets(inputString);
        // then
        assertThat(clusters).isEqualTo(expectedClusters);
    }

    @ParameterizedTest
    @ValueSource(strings = {")(", "(()", "())", "() (()))"})
    @DisplayName("Неправильная скобочная последовательность")
    void notBalancedSequenceTest() {
        // given
        String inputString = ")(";

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> Task2.clusterizeBrackets(inputString));

        String expectedMessage = "Bracket sequence is not balanced!";
        String actualMessage = exception.getMessage();

        // then
        assertThat(expectedMessage).isEqualTo(actualMessage);
    }

}
