package edu.hw3;

import java.util.ArrayList;
import java.util.LinkedHashMap;

@SuppressWarnings("MagicNumber")
public class Task4 {
    private Task4() {
    }

    private static final LinkedHashMap<Integer, String> TO_ROMAN_NUMBERS = new LinkedHashMap<>() {{
        put(1, "I");
        put(4, "IV");
        put(5, "V");
        put(9, "IX");
        put(10, "X");
        put(40, "XL");
        put(50, "L");
        put(90, "XC");
        put(100, "C");
        put(400, "CD");
        put(500, "D");
        put(900, "CM");
        put(1000, "M");
    }};

    private static final int MIN_NUMBER = 1;
    private static final int MAX_NUMBER = 3999;
    private static final int TEN = 10;
    private static final int HUNDRED = 100;
    private static final int THOUSAND = 1000;

    public static String convertToRoman(Integer input) {
        ArrayList<String> resultArray = new ArrayList<>();

        if (input < MIN_NUMBER || input > MAX_NUMBER) {
            throw new IllegalArgumentException("Number out of range!");
        }

        int numOfThousand = input / THOUSAND;
        int tempNumber = numOfThousand * THOUSAND;
        int numOfHundred = (input - (tempNumber)) / HUNDRED;
        tempNumber += numOfHundred * HUNDRED;
        int numOfDecade = (input - (tempNumber)) / TEN;
        tempNumber += numOfDecade * TEN;
        int numOfDigits = input - (tempNumber);

        for (int i = 0; i < numOfThousand; i++) {
            resultArray.add(TO_ROMAN_NUMBERS.get(THOUSAND));
        }

        checkNumber(numOfHundred, HUNDRED, resultArray);
        checkNumber(numOfDecade, TEN, resultArray);
        checkNumber(numOfDigits, 1, resultArray);

        StringBuilder resultStrBuilder = new StringBuilder(resultArray.size());
        for (String str : resultArray) {
            resultStrBuilder.append(str);
        }
        return resultStrBuilder.toString();
    }

    private static void checkNumber(int numOfDigit, int decade, ArrayList<String> resultArray) {
        if (numOfDigit == 9 || numOfDigit == 4) {
            resultArray.add(TO_ROMAN_NUMBERS.get(numOfDigit * decade));
        } else if (numOfDigit >= 5) {
            resultArray.add(TO_ROMAN_NUMBERS.get(5 * decade));
            for (int i = 0; i < numOfDigit - 5; i++) {
                resultArray.add(TO_ROMAN_NUMBERS.get(decade));
            }
        } else {
            for (int i = 0; i < numOfDigit; i++) {
                resultArray.add(TO_ROMAN_NUMBERS.get(decade));
            }
        }
    }

}
