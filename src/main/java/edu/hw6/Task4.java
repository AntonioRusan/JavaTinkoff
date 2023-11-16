package edu.hw6;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;

public class Task4 {
    private Task4() {
    }

    public static void chainOutputWritingToFile(String path, String text) throws Exception {
        try (
            OutputStream fileOutput = Files.newOutputStream(Paths.get(path));
            CheckedOutputStream checkedOutput = new CheckedOutputStream(fileOutput, new Adler32());
            BufferedOutputStream bufferedOutput = new BufferedOutputStream(checkedOutput);
            OutputStreamWriter outputWriter = new OutputStreamWriter(bufferedOutput, StandardCharsets.UTF_8);
            PrintWriter printWriter = new PrintWriter(outputWriter);
        ) {
            printWriter.write(text);
        } catch (Exception ex) {
            throw new Exception("Error writing to file!");
        }
    }
}
