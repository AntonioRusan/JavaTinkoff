package edu.hw1;

import java.util.Arrays;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Task7 {
    private Task7() {
    }

    private final static Logger LOGGER = LogManager.getLogger();
    private static final int INT_SIZE = 32;
    private static final int ONE_BEFORE_INT_SIZE = 31;

    public static int rotateRight(int n, int shift) {
        if (n < 0) {
            return -1;
        }
        int countOfLeadingZeros = getNumberOfLeadingZeros(n);
        int result = (n >> shift)
            |
            ((n << (INT_SIZE - countOfLeadingZeros - shift)) & getNumberOfLeadingZerosAndLastOnes(countOfLeadingZeros));
        printLogs(n, shift, result);
        return result;
    }

    public static int rotateLeft(int n, int shift) {
        if (n < 0) {
            return -1;
        }
        int countOfLeadingZeros = getNumberOfLeadingZeros(n);
        int result = ((n << shift) & getNumberOfLeadingZerosAndLastOnes(countOfLeadingZeros))
            | ((n >> (INT_SIZE - countOfLeadingZeros - shift)));
        printLogs(n, shift, result);
        return result;
    }

    /**
     * Returns count of leading zeroes in a binary representation of a number.
     * For example: getNumberOfLeadingZeros(14 (00...01101)) -> 28
     *
     * @param inputNumber the input number
     * @return count of leading zeros in a binary representation of input number
     */
    private static int getNumberOfLeadingZeros(int inputNumber) {
        int number = inputNumber;
        int cnt = 0;
        int firstOne = (1 << ONE_BEFORE_INT_SIZE);
        while ((number & firstOne) == 0) {
            number = (number << 1);
            cnt++;
        }
        return cnt;
    }

    /**
     * Returns int number with first (n) leading zeros and (INT_SIZE - 1) ones at the end.
     * For example: getNumberOfLeadingZerosAndLastOnes(28) -> 15 (00...01111)
     *
     * @param countOfLeadingZeros the number of leading zeros
     * @return new int number
     */
    private static int getNumberOfLeadingZerosAndLastOnes(int countOfLeadingZeros) {
        return (1 << (INT_SIZE - countOfLeadingZeros)) - 1;
    }

    private static String getBinaryNumber(int inputNumber) {
        int cnt = ONE_BEFORE_INT_SIZE;
        char[] result = new char[INT_SIZE];
        int ind = 0;
        while (cnt >= 0) {
            if ((inputNumber & (1 << cnt)) != 0) {
                result[ind] = '1';
            } else {
                result[ind] = '0';
            }
            cnt--;
            ind++;
        }
        return Arrays.toString(result);
    }

    private static void printLogs(int number, int shift, int result) {
        LOGGER.info("Input number:   " + number + ", shift: " + shift);
        LOGGER.info("Binary number:  " + getBinaryNumber(number));
        LOGGER.info("Shifted number: " + getBinaryNumber(result));
    }

}
