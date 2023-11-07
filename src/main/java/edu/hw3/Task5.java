package edu.hw3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.jetbrains.annotations.Nullable;

public class Task5 {
    private Task5() {

    }

    public static ArrayList<Person> parseContacts(@Nullable ArrayList<String> inputArray, SortOrder order) {
        ArrayList<Person> resultPersons = new ArrayList<>();
        if (inputArray == null || inputArray.isEmpty()) {
            return resultPersons;
        }
        var comparator = switch (order) {
            case ASC -> new PersonComparator();
            case DESC -> Collections.reverseOrder(new PersonComparator());
        };
        for (var str : inputArray) {
            String[] words = str.split(" ");
            String name;
            String surname;
            if (words.length < 2) {
                name = words[0];
                surname = "";
            } else {
                name = words[0];
                surname = words[1];
            }
            resultPersons.add(new Person(name, surname));
        }
        Collections.sort(resultPersons, comparator);
        return resultPersons;
    }

    public static ArrayList<String> personArrayToStrings(ArrayList<Person> inputArray) {
        ArrayList<String> resultArray = new ArrayList<>();
        inputArray.forEach(value -> resultArray.add(value.getAsString()));
        return resultArray;
    }

    public static ArrayList<Person> stringArrayToPersons(ArrayList<String> inputArray) {
        ArrayList<Person> resultArray = new ArrayList<>();
        inputArray.forEach(value -> {
            String[] words = value.split(" ");
            String surname;
            String name;
            if (words.length < 2) {
                name = words[0];
                surname = "";
            } else {
                name = words[0];
                surname = words[1];
            }
            resultArray.add(new Person(name, surname));
        });

        return resultArray;
    }

    public static class PersonComparator implements Comparator<Person> {
        @Override
        public int compare(Person person1, Person person2) {
            if (person1.surname.isEmpty() && person2.surname.isEmpty()) {
                return CharSequence.compare(person1.name, person2.name);
            } else if (person1.surname.isEmpty()) {
                return CharSequence.compare(person1.name, person2.surname);
            } else if (person2.surname.isEmpty()) {
                return CharSequence.compare(person1.surname, person2.name);
            } else {
                return CharSequence.compare(person1.surname, person2.surname);
            }
        }

    }

    public record Person(String name, String surname) {
        public String getAsString() {
            if (name.isEmpty()) {
                return surname;
            } else {
                return name + " " + surname;
            }
        }
    }

    public enum SortOrder {
        ASC,
        DESC,
    }

}
