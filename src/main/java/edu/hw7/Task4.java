package edu.hw7;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("MagicNumber")
public class Task4 {
    private Task4() {
    }

    private final static Logger LOGGER = LogManager.getLogger();

    public static boolean inCircle(Point p, double radius) {
        return (p.x * p.x + p.y * p.y <= radius * radius);
    }

    public static double countPIMonteCarlo(int numOfIter, double radius) {
        double circleCount = 0;
        double totalCount = 0;
        Random random = new Random();
        for (int i = 0; i < numOfIter; i++) {
            double x = random.nextDouble(radius + 1);
            double y = random.nextDouble(radius + 1);
            if (inCircle(new Point(x, y), radius)) {
                circleCount++;
            }
            totalCount++;
        }
        return 4 * (circleCount / totalCount);
    }

    public static double countPIMonteCarloMultiThread(int numThreads, int totalCount, double radius) {
        PIMonteCarloWithLock piMonteCarlo = new PIMonteCarloWithLock();
        List<Thread> threadList = new ArrayList<>();
        int numOfIter = totalCount / numThreads;
        for (int i = 0; i < numThreads; i++) {
            threadList.add(new Thread(() -> piMonteCarlo.countInCircle(numOfIter, radius)));
        }
        for (var thread : threadList) {
            thread.start();
        }
        try {
            for (var thread : threadList) {
                thread.join();
            }
            return 4 * ((double) piMonteCarlo.totalCircleCount.get() / (double) totalCount);
        } catch (Exception ex) {
            LOGGER.info("Exception: " + ex.getMessage());
            return -1;
        }
    }

    public static class PIMonteCarloWithLock {
        Lock lock = new ReentrantLock(true);
        public volatile AtomicLong totalCircleCount = new AtomicLong();

        public void countInCircle(int numOfIter, double radius) {

            long curCircleCount = 0;
            for (int i = 0; i < numOfIter; i++) {
                double x = ThreadLocalRandom.current().nextDouble(radius + 1);
                double y = ThreadLocalRandom.current().nextDouble(radius + 1);
                if (inCircle(new Point(x, y), radius)) {
                    curCircleCount++;
                }
            }
            lock.lock();
            try {
                totalCircleCount.addAndGet(curCircleCount);
            } finally {
                lock.unlock();
            }
        }
    }

    public record Point(double x, double y) {
    }

}

