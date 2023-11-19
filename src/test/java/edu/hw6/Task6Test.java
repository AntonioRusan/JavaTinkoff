package edu.hw6;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw6.Task6.scanAllPorts;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task6Test {

    @Test
    @DisplayName("Проверка получения наиболее обсуждаемых новостей")
    void checkScanAllPorts() {
        // given
        int expectedPortsNumber = 49152 * 2;
        //when
        List<String> actualResult = scanAllPorts();
        for (var item : actualResult) {
            System.out.println(item);
        }
        assertThat(actualResult.size() - 1).isEqualTo(expectedPortsNumber);
        //then
    }
}
