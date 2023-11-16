package edu.hw6;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Predicate;

public class Task3 {

    public interface AbstractFilter extends DirectoryStream.Filter<Path> {

        default AbstractFilter and(AbstractFilter filter) {
            Predicate<Path> predicateAnd = (path -> {
                try {
                    return this.accept(path) && filter.accept(path);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            return predicateAnd::test;
        }

        default AbstractFilter or(AbstractFilter filter) {
            Predicate<Path> predicateOr = (path -> {
                try {
                    return this.accept(path) || filter.accept(path);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            return predicateOr::test;
        }

        static AbstractFilter readable() {
            Predicate<Path> predicateIsReadable = Files::isReadable;
            return predicateIsReadable::test;
        }

        static AbstractFilter writable() {
            Predicate<Path> predicateIsWritable = Files::isWritable;
            return predicateIsWritable::test;
        }

        static AbstractFilter regularFile() {
            Predicate<Path> predicateIsRegularFile = Files::isRegularFile;
            return predicateIsRegularFile::test;
        }

        static AbstractFilter sizeLargerThan(long size) {
            Predicate<Path> predicateSizeLargerThan = (path -> {
                try {
                    return Files.size(path) > size;
                } catch (IOException e) {
                    return false;
                }
            });
            return predicateSizeLargerThan::test;
        }

        static AbstractFilter fileExtension(String extension) {
            Predicate<Path> predicateFileExtension = (path -> path.getFileName().toString().endsWith(extension));
            return predicateFileExtension::test;
        }

        static AbstractFilter filePathMatchesRegex(String regex) {
            Predicate<Path> predicateFilePathMatchesRegex = (path -> path.getFileName().toString().matches(regex));
            return predicateFilePathMatchesRegex::test;
        }

        static AbstractFilter globMatches(String glob) {
            Predicate<Path> predicateGlobMatches = (path -> path.getFileName().toString().matches(globToRegex(glob)));
            return predicateGlobMatches::test;
        }

        static String globToRegex(String glob) {
            StringBuilder regex = new StringBuilder();
            for (int i = 0; i < glob.length(); i++) {
                char c = glob.charAt(i);
                switch (c) {
                    case '*' -> regex.append(".*");
                    case '?' -> regex.append(".");
                    case '.' -> regex.append("\\.");
                    default -> regex.append(c);
                }
            }
            return regex.toString();
        }

        static AbstractFilter magicNumber(int... magicNumbers) {
            Predicate<Path> predicateMagicNumber = (path -> {
                try (InputStream is = Files.newInputStream(path)) {
                    byte[] bytes = new byte[magicNumbers.length];
                    int bytesRead = is.read(bytes, 0, magicNumbers.length);
                    if (bytesRead < magicNumbers.length) {
                        return false;
                    }
                    for (int i = 0; i < magicNumbers.length; i++) {
                        if ((byte) magicNumbers[i] != bytes[i]) {
                            return false;
                        }
                    }
                    return true;
                } catch (IOException e) {
                    return false;
                }
            });
            return predicateMagicNumber::test;
        }
    }

}
