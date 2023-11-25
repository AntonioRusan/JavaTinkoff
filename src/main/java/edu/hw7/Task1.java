package edu.hw7;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Task1 {
    private Task1() {
    }

    public static Integer threadsIncrement(int numThreads, int numOfIncrement) {
        var value = new AtomicInteger();
        List<Thread> incThreads = new ArrayList<>();
        for (int i = 0; i < numThreads; i++) {
            incThreads.add(new Thread(() -> {
                for (int j = 0; j < numOfIncrement; j++) {
                    value.incrementAndGet();
                }
            }));
        }
        for (var thread : incThreads) {
            thread.start();
        }
        try {
            for (var thread : incThreads) {
                thread.join();
            }
            return value.get();
        } catch (InterruptedException ex) {
            throw new RuntimeException("Exception in parallel increment:" + ex.getMessage());
        }
    }
}
