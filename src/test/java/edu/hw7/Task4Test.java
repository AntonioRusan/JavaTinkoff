package edu.hw7;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SuppressWarnings("MagicNumbers")
public class Task4Test {
    double TRUE_PI = 3.14159265358;

    @Test
    @DisplayName("Проверка однопоточного вычисления числа Пи")
    void checkPI() {
        var result = Task4.countPIMonteCarlo(1_000_000_000, 102400);
        double epsilon = 0.0001d;
        System.out.println(result);
        assertThat(Math.abs(TRUE_PI - result) < epsilon).isTrue();;
    }

    @Test
    @DisplayName("Проверка многопоточного вычисления числа Пи")
    void checkPIMultiThreads() {
        int cores = Runtime.getRuntime().availableProcessors();
        System.out.printf("Число ядер процессора: %d%n%n", cores);
        int[] iterations = new int[] {10_000_000, 100_000_000, 1_000_000_000};
        int[] threads = new int[] {1, 2, 4, 8, 12, 32, 100};

        for (var iterNum : iterations) {
            for (var threadNum : threads) {
                long startTime = System.currentTimeMillis();
                double result = Task4.countPIMonteCarloMultiThread(threadNum, iterNum, 102400);
                long endTime = System.currentTimeMillis();
                long timeElapsed = endTime - startTime;
                double delta = Math.abs(TRUE_PI - result);
                double errorPercent = delta / TRUE_PI * 100;
                System.out.printf(
                    "Число точек: %d\tЧисло потоков: %d\tВремя выполнения(в миллисекундах): %d\tПогрешность (в %%): %f\t%n",
                    iterNum,
                    threadNum,
                    timeElapsed,
                    errorPercent
                );
            }
            System.out.println();
        }
    }
}
