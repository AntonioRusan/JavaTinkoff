package edu.hw6;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Task2 {
    private Task2() {
    }

    public static void cloneFile(Path path) throws IOException {
        File originalFile = new File(String.valueOf(path));
        String originalFileName = originalFile.getName();
        String fileExtension = getFileExtension(originalFile);
        String fileNameWithoutExt = getFileNameWithoutExtension(originalFile);
        int countCopy = 1;
        String newFileName = originalFileName;
        String numOfCopy = "";
        boolean hasCopy = Files.exists(path.resolveSibling(newFileName));
        while (hasCopy) {
            newFileName = fileNameWithoutExt + " — копия";
            if (countCopy > 1) {
                numOfCopy = " (" + countCopy + ")";
            }
            newFileName += numOfCopy + fileExtension;
            countCopy++;
            hasCopy = Files.exists(path.resolveSibling(newFileName));
        }
        try {
            Files.copy(path, path.resolveSibling(newFileName));
        } catch (IOException e) {
            throw new IOException("Error while cloning file!");
        }
    }

    private static String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";
        }
        return name.substring(lastIndexOf);
    }

    private static String getFileNameWithoutExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return name;
        }
        return name.substring(0, lastIndexOf);
    }

}
