package edu.hw1;

public final class Task2 {

    private static final int DECADE_DIVISOR = 10;

    private Task2() {
    }

    public static int countDigits(int inputNumber) {
        int number = Math.abs(inputNumber);
        int count = 0;
        if (number == 0) {
            count = 1;
        } else {
            while (number > 0) {
                count++;
                number /= DECADE_DIVISOR;
            }
        }
        return count;
    }
}
