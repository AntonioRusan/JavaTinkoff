package edu.hw6;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Task1 {
    public static class DiskMap implements Map<String, String> {
        private Map<String, String> innerMap;
        private File storageFile;

        public DiskMap(String filePath) {
            this.innerMap = new HashMap<>();
            this.storageFile = new File(filePath);
            loadMapFromFile();
        }

        private void saveMapToFile() {
            StringBuilder mapToStr = new StringBuilder();
            for (var keyValue : innerMap.entrySet()) {
                mapToStr.append(keyValue.getKey() + ":" + keyValue.getValue() + "\n");
            }
            byte[] strToBytes = mapToStr.toString().getBytes();
            try {
                Path storagePath = storageFile.toPath();
                Files.write(storagePath, strToBytes);
            } catch (Exception ex) {
                throw new RuntimeException("Problem writing to file: " + ex.getMessage());
            }
        }

        private void loadMapFromFile() {
            try {
                Path storagePath = storageFile.toPath();
                if (!Files.exists(storagePath)) {
                    storageFile.createNewFile();
                }
                Stream<String> linesStream = Files.lines(storagePath);
                linesStream.forEach(line -> {
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        innerMap.put(parts[0], parts[1]);
                    }
                });
                linesStream.close();
            } catch (Exception ex) {
                throw new RuntimeException("Problem reading from file: " + ex.getMessage());
            }
        }

        @Override
        public int size() {
            return innerMap.size();
        }

        @Override
        public boolean isEmpty() {
            return innerMap.isEmpty();
        }

        @Override
        public boolean containsKey(Object key) {
            return innerMap.containsKey(key);
        }

        @Override
        public boolean containsValue(Object value) {
            return innerMap.containsValue(value);
        }

        @Override
        public String get(Object key) {
            return innerMap.get(key);
        }

        @Nullable
        @Override
        public String put(String key, String value) {
            String added = innerMap.put(key, value);
            saveMapToFile();
            return added;
        }

        @Override
        public String remove(Object key) {
            String removed = innerMap.remove(key);
            saveMapToFile();
            return removed;
        }

        @Override
        public void putAll(@NotNull Map<? extends String, ? extends String> m) {
            innerMap.putAll(m);
            saveMapToFile();
        }

        @Override
        public void clear() {
            innerMap.clear();
            saveMapToFile();
        }

        @NotNull
        @Override
        public Set<String> keySet() {
            return innerMap.keySet();
        }

        @NotNull
        @Override
        public Collection<String> values() {
            return innerMap.values();
        }

        @NotNull
        @Override
        public Set<Entry<String, String>> entrySet() {
            return innerMap.entrySet();
        }
    }
}
