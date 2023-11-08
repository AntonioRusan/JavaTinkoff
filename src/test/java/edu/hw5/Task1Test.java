package edu.hw5;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Task1Test {
    @Test
    @DisplayName("Правильный номерной знак")
    void getAvgDuration() {
        List<String> sessions = new ArrayList<>() {{
            add("2022-03-12, 20:20 - 2022-03-13, 23:50");
            add("2022-04-01, 21:30 - 2022-04-02, 01:20");
            //add("2022-04-03, 21:30 - 2022-04-02, 01:20");
        }};
        String result = Task1.getAvgDuration(sessions);
        System.out.println(result);
    }
}
