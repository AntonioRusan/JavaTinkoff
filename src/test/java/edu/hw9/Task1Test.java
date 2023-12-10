package edu.hw9;

import edu.hw9.Task1.StatsCollector;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task1Test {
    @Test
    void checkStatsCollector() throws InterruptedException {
        List<StatsCollector.StatsResult> expectedBefore = new ArrayList<>() {{
            add(new StatsCollector.StatsResult("metric1", 7.0, 1.75, 4.7, -1.5));
            add(new StatsCollector.StatsResult("metric2", 0.0, 0.0, 1.5, -2.0));
        }};
        List<StatsCollector.StatsResult> expectedAfter = new ArrayList<>() {{
            add(new StatsCollector.StatsResult("metric1", 10, 1.25, 4.7, -1.5));
            add(new StatsCollector.StatsResult("metric2", 0.0, 0.0, 1.5, -2.0));
        }};
        StatsCollector collector = new StatsCollector();

        Thread pushThread1 = new Thread(() -> {
            collector.push("metric1", new double[] {-1.5, 2.5, 4.7, 1.3});
        });
        Thread pushThread2 = new Thread(() -> {
            collector.push("metric2", new double[] {0.5, 1.5, -2.0});
        });
        Thread pushThread3 = new Thread(() -> {
            collector.push("metric1", new double[] {0.5, 1.5, -1.0, 2.0});
        });

        pushThread1.start();
        pushThread2.start();

        var resultStatsBefore = collector.stats();

        pushThread3.start();

        var resultStatsMiddle = collector.stats();
        try {
            pushThread1.join();
            pushThread2.join();
            pushThread3.join();
        } catch (InterruptedException e) {
            System.out.println("Exception" + e);
        }

        var resultStatsAfter = collector.stats();

        assertThat(resultStatsBefore).isEqualTo(expectedBefore);
        assertThat(resultStatsAfter).isEqualTo(expectedAfter);

    }
}
