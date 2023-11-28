package edu.hw8;

import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task2Test {
    private long getFibonacci(int n) {
        long first = 1;
        long second = 1;
        if (n == 1 || n == 2) {
            return first;
        } else if (n > 2) {
            long current = 2;
            for (int i = 3; i <= n; i++) {
                current = first + second;
                second = first;
                first = current;
            }
            return current;
        } else {
            return -1;
        }
    }

    @Test
    void testFibonacciThreadPool() {
        int fibonacciNumber = 50;
        long expectedFibonacci = 12586269025L;
        int numThreads = 10;
        AtomicLong counter = new AtomicLong();
        int numOfIterations = 100_000;
        try (ThreadPool pool = FixedThreadPool.create(numThreads)) {
            pool.start();
            for (int i = 0; i < numOfIterations; i++) {
                pool.execute(() -> {
                    long resultFibonacci = getFibonacci(fibonacciNumber);
                    assertThat(resultFibonacci).isEqualTo(expectedFibonacci);
                    counter.incrementAndGet();
                });
            }
            assertThat(counter.get()).isEqualTo(numOfIterations);
        } catch (Exception ex) {
            System.out.println("Exception:" + ex.getMessage());
        }
    }
}
