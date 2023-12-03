package edu.hw8;

public interface ThreadPool extends AutoCloseable {
    void start();

    void execute(Runnable runnable);
}
