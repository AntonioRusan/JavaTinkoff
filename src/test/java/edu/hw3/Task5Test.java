package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.ArrayList;
import java.util.Arrays;
import static edu.hw3.Task5.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task5Test {

    @Test
    @DisplayName("")
    void simpleGetPersonsTest() {
        // given
        ArrayList<String> inputArray = new ArrayList<>(Arrays.asList("John Locke", "Thomas Aquinas", "David Hume", "Rene Descartes"));
        //ArrayList<String> inputArray = new ArrayList<>(Arrays.asList("Paul Erdos", "Leonhard Euler", "Carl Gauss"));
        // when
        var input = stringArrayToPersons(inputArray);
        var result = parseContacts(inputArray, SortOrder.ASC);
        //var result = parseContacts(inputArray, SortOrder.DESC);
        //var result = parseContacts(null, SortOrder.DESC);
        System.out.println(personArrayToStrings(input));
        System.out.println(personArrayToStrings(result));
        // then
        //assertThat(expectedMessage).isEqualTo(actualMessage);
    }
}
