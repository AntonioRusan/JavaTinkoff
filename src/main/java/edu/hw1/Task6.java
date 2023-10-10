package edu.hw1;

import java.util.Arrays;
import java.util.Collections;

public class Task6 {
    private Task6() {
    }

    private final static int MIN_4_DIGITS_NUMBER = 1001;
    private final static int MAX_4_DIGITS_NUMBER = 9999;
    private final static int KAPREKAR_NUMBER = 6174;
    private final static int DIGIT_NUMBER = 4;
    private final static int DECADE = 10;

    public static int countKaprekar(int inputNumber) {
        int number = inputNumber;
        if (number >= MIN_4_DIGITS_NUMBER && number <= MAX_4_DIGITS_NUMBER) {
            if (number == KAPREKAR_NUMBER) {
                return 0;
            }
            Integer[] digitsSorted = new Integer[DIGIT_NUMBER];
            Integer[] digitsSortedReverse = new Integer[DIGIT_NUMBER];
            int ind = 0;
            while (number > 0) {
                int lastDigit = number % DECADE;
                digitsSorted[ind] = lastDigit;
                digitsSortedReverse[ind] = lastDigit;
                ind++;
                number /= DECADE;
            }
            Arrays.sort(digitsSorted);
            Arrays.sort(digitsSortedReverse, Collections.reverseOrder());
            int minNumber = digitArrayToNumber(digitsSorted);
            int maxNumber = digitArrayToNumber(digitsSortedReverse);
            int diff = maxNumber - minNumber;
            return countKaprekar(diff) + 1;
        } else {
            return -1;
        }
    }

    private static int digitArrayToNumber(Integer[] digitArray) {
        int number = 0;
        int decade = 1;
        for (int i = digitArray.length - 1; i >= 0; i--) {
            number += digitArray[i] * decade;
            decade *= DECADE;
        }
        return number;
    }
}
