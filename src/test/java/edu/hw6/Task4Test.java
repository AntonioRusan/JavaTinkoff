package edu.hw6;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw6.Task4.chainOutputWritingToFile;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class Task4Test {
    private final static Logger LOGGER = LogManager.getLogger();

    @Test
    @DisplayName("Проверка записи в файл")
    void writeToFileWithStreams() {
        // given
        String stringPath = "src/test/resources/hw6/task4Test/SadButTrue.txt";
        String text = "Programming is learned by writing programs. ― Brian Kernighan";

        //when
        assertDoesNotThrow(() -> chainOutputWritingToFile(stringPath, text));
        //then
        Path path = Paths.get(stringPath);
        try {
            String actualText = new String(
                Files.readAllBytes(path));
            assertThat(actualText).isEqualTo(text);
        } catch (Exception ex) {
            LOGGER.info("Exception while reading from file");
        }

        try {
            Files.delete(path);
        } catch (Exception ex) {
            LOGGER.info("Exception while deleting file");
        }
    }
}
