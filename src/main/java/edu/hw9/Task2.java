package edu.hw9;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.function.Predicate;

public class Task2 {

    public static class ParallelFileTreeProcessor {
        private static final int NUM_OF_FILES = 1000;

        public static List<File> findDirectoriesWithMoreThanNumberFiles(File rootDirectory) {
            ForkJoinPool forkJoinPool = new ForkJoinPool();
            return forkJoinPool.invoke(new DirectorySearchTask(rootDirectory));
        }

        public static List<File> findFilesByFilter(File rootDirectory, Predicate<File> predicate) {
            ForkJoinPool forkJoinPool = new ForkJoinPool();
            return forkJoinPool.invoke(new FileSearchTask(rootDirectory, predicate));
        }

        static class DirectorySearchTask extends RecursiveTask<List<File>> {
            private final File directory;

            DirectorySearchTask(File directory) {
                this.directory = directory;
            }

            @Override
            protected List<File> compute() {
                List<DirectorySearchTask> subTasks = new ArrayList<>();
                List<File> largerDirectories = new ArrayList<>();

                File[] subDirectories = directory.listFiles(File::isDirectory);
                if (subDirectories != null) {
                    for (File subDirectory : subDirectories) {
                        DirectorySearchTask subTask = new DirectorySearchTask(subDirectory);
                        subTask.fork();
                        subTasks.add(subTask);
                    }
                }

                for (DirectorySearchTask subTask : subTasks) {
                    largerDirectories.addAll(subTask.join());
                }

                if (directory.listFiles().length > NUM_OF_FILES) {
                    largerDirectories.add(directory);
                }

                return largerDirectories;
            }
        }

        static class FileSearchTask extends RecursiveTask<List<File>> {
            private final File directory;
            private final Predicate<File> predicate;

            FileSearchTask(File directory, Predicate<File> predicate) {
                this.directory = directory;
                this.predicate = predicate;
            }

            @Override
            protected List<File> compute() {
                List<FileSearchTask> subTasks = new ArrayList<>();
                List<File> matchingFiles = new ArrayList<>();

                File[] subDirectories = directory.listFiles(File::isDirectory);
                if (subDirectories != null) {
                    for (File subDirectory : subDirectories) {
                        FileSearchTask subTask = new FileSearchTask(subDirectory, predicate);
                        subTask.fork();
                        subTasks.add(subTask);
                    }
                }

                for (FileSearchTask subTask : subTasks) {
                    matchingFiles.addAll(subTask.join());
                }

                File[] files = directory.listFiles(File::isFile);
                if (files != null) {
                    for (File file : files) {
                        if (predicate.test(file)) {
                            matchingFiles.add(file);
                        }
                    }
                }

                return matchingFiles;
            }
        }
    }

}
