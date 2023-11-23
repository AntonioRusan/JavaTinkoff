package edu.hw5;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task7 {
    private Task7() {

    }

    public static boolean checkThirdIsZero(String input) {
        Pattern pattern = Pattern.compile("^([01]){2}0[01]*$");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public static boolean checkFirstEqualsLast(String input) {
        Pattern pattern = Pattern.compile("^([01])[01]*\\1$|^[01]$");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public static boolean checkLengthFrom1To3(String input) {
        Pattern pattern = Pattern.compile("^[01]{1,3}$");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}
