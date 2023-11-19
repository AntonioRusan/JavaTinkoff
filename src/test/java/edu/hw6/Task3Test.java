package edu.hw6;

import edu.hw6.Task3.AbstractFilter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task3Test {
    @Test
    @DisplayName("Проверка фильтров для текстовых файлов: либо текстовые файлы, либо размер больше 1000 байт")
    void filterTextFiles() {
        // given
        Path dir = Paths.get("src/test/resources/hw6/task3Test");
        Set<String> expectedPaths = new HashSet<>() {{
            add("emptyFile.txt");
            add("filterTest.txt");
            add("obiwan.txt");
            add("papich.jpg");
            add("test.txt");
            add("vault_boy_hacker.png");
            add("vault_boy_nerd.png");
        }};

        //when
        AbstractFilter filter = AbstractFilter.regularFile()
            .and(AbstractFilter.readable())
            .and(AbstractFilter.writable())
            .and(AbstractFilter.globMatches("*.txt"))
            .or(AbstractFilter.sizeLargerThan(1000));
        Set<String> actualPaths = new HashSet<>();
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(dir, filter)) {
            entries.forEach(path -> actualPaths.add(path.getFileName().toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //then
        assertThat(actualPaths).isEqualTo(expectedPaths);
    }

    @Test
    @DisplayName("Проверка фильтров для картинок")
    void filterImages() {
        // given
        Path dir = Paths.get("src/test/resources/hw6/task3Test");
        Set<String> expectedPaths = new HashSet<>() {{
            add("vault_boy_hacker.png");
            add("vault_boy_nerd.png");
        }};
        //when
        AbstractFilter filter = AbstractFilter.regularFile()
            .and(AbstractFilter.readable())
            .and(AbstractFilter.sizeLargerThan(10_000))
            .and(AbstractFilter.magicNumber(0x89, 'P', 'N', 'G'))
            .and(AbstractFilter.globMatches("*.png"))
            .and(AbstractFilter.filePathMatchesRegex(".*vault_boy.*"));
        Set<String> actualPaths = new HashSet<>();
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(dir, filter)) {
            entries.forEach(path -> actualPaths.add(path.getFileName().toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //then
        assertThat(actualPaths).isEqualTo(expectedPaths);
    }
}
