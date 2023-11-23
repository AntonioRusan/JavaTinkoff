package edu.hw5;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task8 {
    private Task8() {

    }

    public static boolean checkOddLength(String input) {
        Pattern pattern = Pattern.compile("^[01]([01]{2})*$");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public static boolean checkOddLengthFromZeroOrEvenLengthFromOne(String input) {
        Pattern pattern = Pattern.compile("^0([01]{2})*|1[01]([01]{2})*$");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public static boolean numberOfZeroDivisible3(String input) {
        Pattern pattern = Pattern.compile("^1*(1*01*01*01*)*");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public static boolean notTwoOrThreeOnes(String input) {
        Pattern pattern = Pattern.compile("^(([01])|((0|10|110|1110|1111)[01]*))$");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public static boolean everyOddIsOne(String input) {
        Pattern pattern = Pattern.compile("^((1[01])*|1([01]1)*)$");
        Matcher matcher = pattern.matcher(input);

        return matcher.matches();
    }

    public static boolean noSubsequentOnes(String input) {
        Pattern pattern = Pattern.compile("^(0*(10+)*1?|1(0+1)*0*)$");
        Matcher matcher = pattern.matcher(input);

        return matcher.matches();
    }

    public static boolean moreThan1ZerosLessThan2One(String input) {
        Pattern pattern = Pattern.compile("^(10{2,}|0*01?0+|0{2,}1?)$");
        Matcher matcher = pattern.matcher(input);

        return matcher.matches();
    }
}
