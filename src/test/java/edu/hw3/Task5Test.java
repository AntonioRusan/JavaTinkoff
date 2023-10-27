package edu.hw3;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw3.Task5.Person;
import static edu.hw3.Task5.SortOrder;
import static edu.hw3.Task5.parseContacts;
import static edu.hw3.Task5.stringArrayToPersons;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task5Test {

    @Test
    @DisplayName("Тест сортировка по возрастанию")
    void getPersonsASCTest() {
        // given
        ArrayList<String> inputArray =
            new ArrayList<>(Arrays.asList("John Locke", "Thomas Aquinas", "David Hume", "Rene Descartes"));
        ArrayList<Person> expectedArray = stringArrayToPersons(new ArrayList<>(Arrays.asList(
            "Thomas Aquinas",
            "Rene Descartes",
            "David Hume",
            "John Locke"
        )));
        // when
        var result = parseContacts(inputArray, SortOrder.ASC);
        // then
        assertThat(result).isEqualTo(expectedArray);
    }

    @Test
    @DisplayName("Тест сортировка по убыванию")
    void getPersonsDESCTest() {
        // given
        ArrayList<String> inputArray = new ArrayList<>(Arrays.asList("Paul Erdos", "Leonhard Euler", "Carl Gauss"));
        ArrayList<Person> expectedArray =
            stringArrayToPersons(new ArrayList<>(Arrays.asList("Carl Gauss", "Leonhard Euler", "Paul Erdos")));
        // when
        var result = parseContacts(inputArray, SortOrder.DESC);
        // then
        assertThat(result).isEqualTo(expectedArray);
    }

    @Test
    @DisplayName("Тест пустой список")
    void getPersonsEmptyListTest() {
        // given
        ArrayList<String> inputArray = new ArrayList<>();
        ArrayList<Person> expectedArray = new ArrayList<>();
        // when
        var result = parseContacts(inputArray, SortOrder.DESC);
        // then
        assertThat(result).isEqualTo(expectedArray);
    }

    @Test
    @DisplayName("Тест null")
    void simpleGetPersonsNullCTest() {
        // given
        ArrayList<String> inputArray = null;
        ArrayList<Person> expectedArray = new ArrayList<>();
        // when
        var result = parseContacts(inputArray, SortOrder.DESC);
        // then
        assertThat(result).isEqualTo(expectedArray);
    }

    @Test
    @DisplayName("Тест с отсутствующими фамилиями")
    void simpleGetPersonsOnlyNameCTest() {
        // given
        ArrayList<String> inputArray =
            new ArrayList<>(Arrays.asList("Paul", "Leonhard Euler", "Arthur", "Carl Gauss", "Artem"));
        ArrayList<Person> expectedArray =
            stringArrayToPersons(new ArrayList<>(Arrays.asList(
                "Artem",
                "Arthur",
                "Leonhard Euler",
                "Carl Gauss",
                "Paul"
            )));
        // when
        var result = parseContacts(inputArray, SortOrder.ASC);
        // then
        assertThat(result).isEqualTo(expectedArray);
    }
}
