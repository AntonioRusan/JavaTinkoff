package edu.project3;

import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"MultipleStringLiterals", "MagicNumber"})
public class LogReport {
    private String sourceName;
    public List<LogItem> logs;
    private String fromDate;
    private String toDate;
    public int totalRequests;
    public Map<String, Integer> topResources = new HashMap<>();
    public Map<Integer, Integer> responseCodes = new HashMap<>();

    private Long averageResponseSize;

    public LogReport(List<LogItem> logs, String sourceName, OffsetDateTime fromDate, OffsetDateTime toDate) {
        this.sourceName = sourceName;
        this.fromDate = fromDate.toString();
        this.toDate = toDate.toString();
        this.logs = filterDateLogs(logs, fromDate, toDate);
        this.calculateAverageResponseSize();
        this.calculateTotalRequests();
        this.calculateResponseCodes();
        this.calculateTopResources();
    }

    public void calculateTotalRequests() {
        totalRequests = logs.size();
    }

    public void calculateTopResources() {
        for (LogItem log : logs) {
            String resource = log.request().resource();
            topResources.put(resource, topResources.getOrDefault(resource, 0) + 1);
        }
    }

    public void calculateResponseCodes() {
        for (LogItem log : logs) {
            responseCodes.put(log.status(), responseCodes.getOrDefault(log.status(), 0) + 1);
        }
    }

    public void calculateAverageResponseSize() {
        Long totalSize = logs.stream()
            .map(LogItem::bodyBytesSent)
            .reduce(0L, Long::sum);
        averageResponseSize = totalSize / logs.size();
    }

    public String getMarkdownReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("#### Общая информация\n\n");
        sb.append("|        Метрика        |     Значение |\n");
        sb.append("|:---------------------:|-------------:|\n");
        sb.append(String.format("|       Файл(-ы)        | %s |\n", sourceName));
        sb.append(String.format("|    Начальная дата     | %s |\n", fromDate));
        sb.append(String.format("|     Конечная дата     | %s |\n", toDate));
        sb.append(String.format("|  Количество запросов  | %,d |\n", totalRequests));
        sb.append(String.format("| Средний размер ответа | %,db |\n\n", averageResponseSize));

        sb.append("#### Запрашиваемые ресурсы\n\n");
        sb.append("|     Ресурс      | Количество |\n");
        sb.append("|:---------------:|-----------:|\n");
        topResources.entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .forEach(e -> sb.append(String.format("|  %s  | %,d |\n", e.getKey(), e.getValue())));
        sb.append("\n");

        sb.append("#### Коды ответа\n\n");
        sb.append("| Код |          Имя          | Количество |\n");
        sb.append("|:---:|:---------------------:|-----------:|\n");
        responseCodes.entrySet().stream()
            .forEach(e -> sb.append(String.format(
                "| %d |     %s      |    %,d |\n",
                e.getKey(),
                getResponseName(e.getKey()),
                e.getValue()
            )));

        return sb.toString();
    }

    public String getAdocReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("==== Общая информация\n\n");
        sb.append("|        Метрика        |     Значение |\n");
        sb.append("|:---------------------:|-------------:|\n");
        sb.append(String.format("|       Файл(-ы)        | %s |\n", sourceName));
        sb.append("|    Начальная дата     |   31.08.2023 |\n");
        sb.append("|     Конечная дата     |            - |\n");
        sb.append(String.format("|  Количество запросов  |    %,d |\n", totalRequests));
        sb.append(String.format("| Средний размер ответа |      %,db |\n\n", averageResponseSize));

        sb.append("==== Запрашиваемые ресурсы\n\n");
        sb.append("|     Ресурс      | Количество |\n");
        sb.append("|:---------------:|-----------:|\n");
        topResources.entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .forEach(e -> sb.append(String.format("|  %s  |    %,d |\n", e.getKey(), e.getValue())));
        sb.append("\n");

        sb.append("==== Коды ответа\n\n");
        sb.append("| Код |          Имя          | Количество |\n");
        sb.append("|:---:|:---------------------:|-----------:|\n");
        responseCodes.entrySet().stream()
            .forEach(e -> sb.append(String.format(
                "|  %d  |     %s      |    %,d |\n",
                e.getKey(),
                getResponseName(e.getKey()),
                e.getValue()
            )));

        return sb.toString();
    }

    private String getResponseName(int statusCode) {
        switch (statusCode) {
            case 200:
                return "OK";
            case 404:
                return "Not Found";
            case 500:
                return "Internal Server Error";
            default:
                return "Unknown";
        }
    }

    public List<LogItem> filterDateLogs(List<LogItem> logs, OffsetDateTime fromDate, OffsetDateTime toDate) {
        return logs.stream().filter(log -> (log.timeLocal().isAfter(fromDate)
            && log.timeLocal().isBefore(toDate)) || log.timeLocal().isEqual(fromDate)
            || log.timeLocal().isEqual(toDate)).toList();
    }
}
