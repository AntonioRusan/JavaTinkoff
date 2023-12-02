package edu.hw8;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CrackPasswordMultiThreadTest {
    @Test
    void testMultiThreadCrackPassword() {
        CrackPasswordMultiThread crackPassword = new CrackPasswordMultiThread(4, 12);
        List<String> userData = new ArrayList<>() {{
            add("a.v.petrov 8201abd06f03cdab98da0a22574678d1");
            add("v.v.belov f6570eda044f61845293ef75f7b53ec1");
            add("a.s.ivanov fa246d0262c3925617b0c72bb20eeb1d");
            add("k.p.maslov 3f8a584b257ce227a336ca8270d90893");
            add("i.i.ivanov 8ce4b16b22b58894aa86c421e8759df3");
            add("v.p.papich 73c18c59a39b18382081ec00bb456d43");
            add("p.p.petrov dd919c61b0a1989ce0fe0e27863722ee");
        }};
        Map<String, String> expectedCracked = new HashMap<>() {{
            put("abob", "a.v.petrov");
            put("h69g", "v.v.belov");
            put("9999", "a.s.ivanov");
            put("6a6", "k.p.maslov");
            put("k", "i.i.ivanov");
            put("gg", "v.p.papich");
            put("k3k", "p.p.petrov");
        }};
        crackPassword.fillMap(userData);
        crackPassword.crackPasswordMultiThread();
        assertThat(crackPassword.hashToUser).isEqualTo(expectedCracked);
    }

    @Test
    void calculateTimeMultiThreadCrackPassword() {
        List<String> userData = new ArrayList<>() {{
            add("a.v.petrov 8201abd06f03cdab98da0a22574678d1");
            add("v.v.belov f6570eda044f61845293ef75f7b53ec1");
            add("a.s.ivanov fa246d0262c3925617b0c72bb20eeb1d");
            add("k.p.maslov 3f8a584b257ce227a336ca8270d90893");
            add("i.i.ivanov 8ce4b16b22b58894aa86c421e8759df3");
            add("v.p.papich 73c18c59a39b18382081ec00bb456d43");
            add("p.p.petrov dd919c61b0a1989ce0fe0e27863722ee");
        }};
        Map<String, String> expectedCracked = new HashMap<>() {{
            put("abob", "a.v.petrov");
            put("h69g", "v.v.belov");
            put("9999", "a.s.ivanov");
            put("6a6", "k.p.maslov");
            put("k", "i.i.ivanov");
            put("gg", "v.p.papich");
            put("k3k", "p.p.petrov");
        }};
        int[] threads = new int[] {1, 2, 4, 8, 12, 32, 100};
        int maxPasswordLength = 4;
        for (var threadNum : threads) {
            CrackPasswordMultiThread crackPassword = new CrackPasswordMultiThread(maxPasswordLength, threadNum);
            crackPassword.fillMap(userData);
            long startTime = System.currentTimeMillis();
            crackPassword.crackPasswordMultiThread();
            long endTime = System.currentTimeMillis();
            long timeElapsed = endTime - startTime;
            System.out.printf(
                "Максимальное число символов пароля: %d\tЧисло потоков: %d\tВремя выполнения(в миллисекундах): %d\t%n",
                maxPasswordLength,
                threadNum,
                timeElapsed
            );
            assertThat(crackPassword.hashToUser).isEqualTo(expectedCracked);
        }
    }
}
