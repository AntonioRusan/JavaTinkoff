package edu.hw9;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Task1 {

    public static class StatsCollector {
        private static final int NUM_OF_THREADS = 4;

        private Map<String, List<Double>> dataStorage;
        private ReentrantReadWriteLock lock;

        public StatsCollector() {
            this.dataStorage = new HashMap<>();
            this.lock = new ReentrantReadWriteLock();
        }

        public void push(String metricName, double[] values) {
            lock.writeLock().lock();
            try {
                if (!dataStorage.containsKey(metricName)) {
                    dataStorage.put(metricName, new ArrayList<>());
                }

                for (double value : values) {
                    dataStorage.get(metricName).add(value);
                }
            } finally {
                lock.writeLock().unlock();
            }
        }

        public List<StatsResult> stats() {
            ExecutorService executor = Executors.newFixedThreadPool(NUM_OF_THREADS);
            List<StatsResult> results = new ArrayList<>();
            lock.readLock().lock();
            try {
                for (var entry : dataStorage.entrySet()) {
                    String metricName = entry.getKey();
                    List<Double> values = entry.getValue();
                    Future<Double> sumFuture =
                        executor.submit(() -> values.stream().mapToDouble(Double::doubleValue).sum());
                    Future<Double> avgFuture =
                        executor.submit(() -> values.stream().mapToDouble(Double::doubleValue).average().orElse(0.0));
                    Future<Double> maxFuture =
                        executor.submit(() -> values.stream().mapToDouble(Double::doubleValue).max().orElse(0.0));
                    Future<Double> minFuture =
                        executor.submit(() -> values.stream().mapToDouble(Double::doubleValue).min().orElse(0.0));

                    StatsResult result = new StatsResult(
                        metricName,
                        sumFuture.get(),
                        avgFuture.get(),
                        maxFuture.get(),
                        minFuture.get()
                    );
                    results.add(result);
                }
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.readLock().unlock();
            }
            return results;
        }

        public record StatsResult(
            String metricName,
            double sum,
            double avg,
            double max,
            double min
        ) {
        }
    }

}
