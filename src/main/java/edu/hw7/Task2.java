package edu.hw7;

import java.util.stream.LongStream;

public class Task2 {
    private Task2() {
    }

    public static Long getStreamParallelFactorial(int number) {
        if (number < 0) {
            return -1L;
        }
        return LongStream.rangeClosed(1L, number).boxed().parallel().reduce(1L, (x, y) -> x * y);
    }
}
