package edu.hw3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;
import org.jetbrains.annotations.Nullable;

public class Task5 {
    private Task5() {

    }

    public static record Person(String name, String surname) {
        public String getAsString() {
            if (name.isEmpty()){
                return surname;
            }
            else {
                return name + " " + surname;
            }
        }
    }

    public static enum SortOrder {
        ASC,
        DESC,
    }

    public static ArrayList<Person> parseContacts(@Nullable ArrayList<String> inputArray, SortOrder order) {
        ArrayList<Person> resultPersons = new ArrayList<>();
        if (inputArray == null || inputArray.isEmpty()) {
            return resultPersons;
        }
        var comparator = switch (order) {
            case ASC -> null;
            case DESC -> Collections.reverseOrder();
        };
        TreeMap<String, String> surNameAndName = new TreeMap<>(comparator);
        for (var str : inputArray) {
            String[] words = str.split(" ");
            String key, value;
            if (words.length < 2) {
                key = words[0];
                value = "";
            } else {
                key = words[1];
                value = words[0];
            }
            surNameAndName.put(key, value);
        }
        surNameAndName.forEach((k, v) -> resultPersons.add(new Person(v, k)));
        return resultPersons;
    }

    public static ArrayList<String> personArrayToStrings(ArrayList<Person> inputArray){
        ArrayList<String> resultArray = new ArrayList<>();
        inputArray.forEach(value -> resultArray.add(value.getAsString()));
        return resultArray;
    }

    public static ArrayList<Person> stringArrayToPersons(ArrayList<String> inputArray){
        ArrayList<Person> resultArray = new ArrayList<>();
        inputArray.forEach(value ->{
            String[] words = value.split(" ");
            String surname, name;
            if (words.length < 2) {
                surname = words[0];
                name = "";
            } else {
                surname = words[1];
                name = words[0];
            }
            resultArray.add(new Person(name, surname));
        });

        return resultArray;
    }

}
