package edu.hw6;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw6.Task2.cloneFile;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task2Test {
    private final static Logger LOGGER = LogManager.getLogger();

    @Test
    @DisplayName("Проверка создания копий")
    void checkCloneFile() {
        // given
        Path originalPath = Paths.get("src/test/java/edu/hw6/task2test/Tinkoff Bank Biggest Secret.txt");
        List<Path> expecteCopyPaths = new ArrayList<>() {{
            add(Paths.get("src/test/java/edu/hw6/task2test/Tinkoff Bank Biggest Secret — копия.txt"));
            add(Paths.get("src/test/java/edu/hw6/task2test/Tinkoff Bank Biggest Secret — копия (2).txt"));
            add(Paths.get("src/test/java/edu/hw6/task2test/Tinkoff Bank Biggest Secret — копия (3).txt"));
        }};
        for (var path : expecteCopyPaths) {
            assertDoesNotThrow(() -> cloneFile(originalPath));
            assertTrue(Files.exists(path));
        }
        // when
        // then
        for (var path : expecteCopyPaths) {
            try {
                Files.delete(path);
            } catch (Exception ex) {
                LOGGER.info("Error while deleting copies!");
            }
        }
    }
}
