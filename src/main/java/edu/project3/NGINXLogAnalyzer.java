package edu.project3;

import java.net.URL;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.List;

public class NGINXLogAnalyzer {
    public NGINXLogAnalyzer() {
    }

    public void analyzeLogs(String[] args) {
        try {
            String path = "";
            String from = "";
            String to = "";
            String format = "";

            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("--path")) {
                    path = args[i + 1];
                } else if (args[i].equals("--from")) {
                    from = args[i + 1];
                } else if (args[i].equals("--to")) {
                    to = args[i + 1];
                } else if (args[i].equals("--format")) {
                    format = args[i + 1];
                }
            }

            //OffsetDateTime fromDate = OffsetDateTime.parse(from);
            //OffsetDateTime toDate = OffsetDateTime.parse(to);

            List<LogItem> logs = loadFile(path);
            LogReport report = new LogReport(logs, from, to);
            if (format.equalsIgnoreCase("markdown")) {
                System.out.println(report.getMarkdownReport());
            } else if (format.equalsIgnoreCase("adoc")) {
                System.out.println(report.getAdocReport());
            }
        } catch (Exception ex) {
            System.out.println("Failed to analyze logs: " + ex.getMessage());
        }
    }

    private List<LogItem> loadFile(String path) {
        if (isUrlValid(path)) {
            return LogUrlReader.readLogsFromUrl(path);
        } else if (isPathValid(path)) {
            return LogFileReader.readLogsFromFile(path);
        } else {
            throw new IllegalArgumentException("Wrong log source path!");
        }
    }

    private static boolean isUrlValid(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isPathValid(String path) {
        try {
            Paths.get(path);
        } catch (InvalidPathException ex) {
            return false;
        }
        return true;
    }
}
