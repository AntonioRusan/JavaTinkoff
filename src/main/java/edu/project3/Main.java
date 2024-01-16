package edu.project3;

public class Main {
    private Main() {
    }

    public static void main(String[] args) {
        NGINXLogAnalyzer analyzer = new NGINXLogAnalyzer();
        analyzer.analyzeLogs(args);
    }
}
