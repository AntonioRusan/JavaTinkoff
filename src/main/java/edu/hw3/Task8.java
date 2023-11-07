package edu.hw3;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class Task8 {
    private Task8() {

    }

    public static class BackwardIterator<T> implements Iterator<T> {
        private final List<T> collection;
        private int currentPosition;

        public BackwardIterator(List<T> collection) {
            this.collection = collection;
            this.currentPosition = collection.size();
        }

        @Override
        public boolean hasNext() {
            return currentPosition >= 1;
        }

        @Override
        public T next() {
            if (hasNext()) {
                currentPosition--;
                return collection.get(currentPosition);
            } else {
                throw new NoSuchElementException();
            }
        }
    }
}
