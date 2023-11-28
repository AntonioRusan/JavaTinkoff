package edu.project3;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class LogFileReader {
    private LogFileReader() {
    }

    public static List<LogItem> readLogsFromFile(String inputPath) {
        try {
            PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + inputPath);
            Path path = Paths.get(inputPath);
            if (matcher.matches(path)) {
                return (Files.lines(path)
                    .map(LogParser::parseLogString)
                    .toList());
            } else {
                Stream<LogItem> logItemStream = Files.walk(path)
                    .filter(Files::isRegularFile).flatMap(file -> {
                            try {
                                return Files.lines(file).map(LogParser::parseLogString);
                            } catch (IOException e) {
                                throw new RuntimeException();
                            }
                        }
                    );
                return logItemStream.toList();
            }
        } catch (IOException ex) {
            throw new RuntimeException("Error reading logs from file: " + ex.getMessage());
        }
    }
}
