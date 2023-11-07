package edu.hw5;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task5 {
    private Task5() {

    }

    public static boolean checkRussianLicensePlate(String input) {
        Pattern pattern = Pattern.compile("[ABEKMHOPCTYX]\\d{3}[ABEKMHOPCTYX][ABEKMHOPCTYX]((\\d[1-9])|([1-9]\\d{2}))");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}
