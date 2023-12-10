package edu.hw9;

import edu.hw9.Task2.ParallelTreeProcessor;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task2Test {
    public static String rootPath = "src/test/resources/hw9";

    @Test
    @DisplayName("поиск директорий, в которых больше 1000 файлов")
    public void testFindDirectoriesWithMoreThan1000Files() {

        for (int counter = 0; counter < 1001; counter++) {
            String path = "src/test/resources/hw9/task2Test/manyFiles/manyManyFiles/" + "test" + counter + "." + "txt";
            try {
                File file = new File(path);
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        List<File> largeDirectories = ParallelTreeProcessor.findDirectoriesWithMoreThan1000Files(new File(rootPath));
        assertNotNull(largeDirectories);
        for (File dir : largeDirectories) {
            assertTrue(dir.listFiles().length > 1000);
        }

        for (int counter = 0; counter < 1001; counter++) {
            String path = "src/test/resources/hw9/task2Test/manyFiles/manyManyFiles/" + "test" + counter + "." + "txt";
            File file = new File(path);
            file.delete();
        }

    }

    @Test
    @DisplayName("поиск файлов по предикату: размер")
    public void testFindFilesBySize() {
        File rootDirectory = new File(rootPath);
        List<File> matchingFiles = ParallelTreeProcessor.findFiles(rootDirectory, file -> file.length() > 3);
        assertNotNull(matchingFiles);
        for (File file : matchingFiles) {
            assertTrue(file.length() > 3);
        }
    }

    @Test
    @DisplayName("поиск файлов по предикату: расширение")
    public void testFindFilesByExtension() {
        File rootDirectory = new File(rootPath);
        List<File> matchingFiles =
            ParallelTreeProcessor.findFiles(rootDirectory, file -> file.getName().endsWith(".json"));
        assertNotNull(matchingFiles);
        for (File file : matchingFiles) {
            assertTrue(file.getName().endsWith(".json"));
        }
    }
}
