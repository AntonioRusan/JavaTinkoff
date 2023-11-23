package edu.hw5;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task4 {
    private Task4() {

    }

    public static boolean checkPassword(String input) {
        Pattern pattern = Pattern.compile("(.*)[~!@#$%^&*|]+(.*)");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}
