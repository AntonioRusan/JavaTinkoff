package edu.hw6;

import edu.hw6.Task1.DiskMap;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task1Test {
    private final static Logger LOGGER = LogManager.getLogger();

    @Test
    @DisplayName("Проверка создания DiskMap из файла")
    void createDiskMap() {
        // given
        String stringPath = "src/test/resources/hw6/task1Test/diskMap.txt";
        Map<String, String> expected = new HashMap<>() {{
            put("1_key", "first");
            put("2_key", "second");
            put("3_key", "third");
        }};
        //when
        DiskMap diskMap = new DiskMap(stringPath);
        //then
        assertThat(diskMap.entrySet()).isEqualTo(expected.entrySet());
    }

    @Test
    @DisplayName("Проверка добавления пары в DiskMap и потом удаления")
    void putAndRemoveToDiskMap() {
        // given
        String stringPath = "src/test/resources/hw6/task1Test/changeDiskMap.txt";
        Map<String, String> expected = new HashMap<>() {{
            put("init", "i'm first");
            put("added", "i'm added");
        }};
        //when
        DiskMap diskMap = new DiskMap(stringPath);
        diskMap.put("added", "i'm added");
        //then
        assertThat(diskMap.entrySet()).isEqualTo(expected.entrySet());

        diskMap.remove("added");
        expected.remove("added");
        assertThat(diskMap.entrySet()).isEqualTo(expected.entrySet());
    }

    @Test
    @DisplayName("Проверка добавления мапа в DiskMap и потом очистки")
    void putAllAndClearToDiskMap() {
        // given
        String stringPath = "src/test/resources/hw6/task1Test/changeDiskMap.txt";
        Map<String, String> expected = new HashMap<>() {{
            put("init", "i'm first");
            put("added", "i'm added");
            put("put", "i'm put");
            put("insert", "i'm insert");
        }};
        Map<String, String> addedMap = new HashMap<>() {{
            put("added", "i'm added");
            put("put", "i'm put");
            put("insert", "i'm insert");
        }};
        //when
        DiskMap diskMap = new DiskMap(stringPath);
        diskMap.putAll(addedMap);
        //then
        assertThat(diskMap.entrySet()).isEqualTo(expected.entrySet());

        diskMap.clear();
        assertThat(diskMap.isEmpty()).isTrue();

        diskMap.put("init", "i'm first");
    }

    @Test
    @DisplayName("Проверка создания DiskMap с несуществующим файлом и тестировнаие других методов")
    void createNewFileWithDiskMap() {
        // given
        String stringPath = "src/test/resources/hw6/task1Test/createNewDiskMap.txt";
        Path path = Paths.get(stringPath);
        assertThat(Files.exists(path)).isFalse();
        Map<String, String> expected = new HashMap<>() {{
            put("added", "i'm added");
            put("second", "i'm second");
        }};
        //when
        DiskMap diskMap = new DiskMap(stringPath);
        //then
        assertThat(Files.exists(path)).isTrue();

        diskMap.put("added", "i'm added");
        diskMap.put("second", "i'm second");

        assertThat(diskMap.entrySet()).isEqualTo(expected.entrySet());
        assertThat(diskMap.size()).isEqualTo(expected.size());
        assertThat(diskMap.containsKey("added")).isTrue();
        assertThat(diskMap.containsKey("not_added")).isFalse();
        assertThat(diskMap.containsValue("i'm added")).isTrue();
        assertThat(diskMap.containsKey("i'm not added")).isFalse();
        assertThat(diskMap.get("added")).isEqualTo("i'm added");
        assertThat(diskMap.keySet()).isEqualTo(expected.keySet());
        assertThat(diskMap.values().toArray()).isEqualTo(expected.values().toArray());

        try {
            Files.delete(path);
        } catch (Exception ex) {
            LOGGER.info("Exception while deleting file");
        }
    }
}
