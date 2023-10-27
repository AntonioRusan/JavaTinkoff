package edu.hw3;

import java.util.Comparator;

public class Task7 {
    private Task7() {

    }

    public static class NullComparator<T extends Comparable<T>> implements Comparator<T> {
        public int compare(T x, T y) {
            if (x == null && y == null) {
                return 0;
            } else if (x == null) {
                return -1;
            } else if (y == null) {
                return 1;
            } else {
                return x.compareTo(y);
            }
        }

    }

}
