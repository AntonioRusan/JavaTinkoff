package edu.hw3;

import java.util.PriorityQueue;

public class Task6 {
    private Task6() {

    }

    public record Stock(double price) implements Comparable<Stock> {
        @Override
        public int compareTo(Stock other) {
            return Double.compare(other.price, this.price);
        }
    }

    interface StockMarket {
        /** Добавить акцию */
        void add(Stock stock);

        /** Удалить акцию */
        void remove(Stock stock);

        /** Самая дорогая акция */
        Stock mostValuableStock();

        boolean isEmpty();
    }

    public static class MyStockMarket implements StockMarket {
        PriorityQueue<Stock> stocks = new PriorityQueue<>();

        public MyStockMarket() {
        }

        @Override
        public void add(Stock stock) {
            stocks.add(stock);
        }

        @Override
        public void remove(Stock stock) {
            stocks.remove(stock);
        }

        @Override
        public Stock mostValuableStock() {
            return stocks.peek();
        }

        @Override
        public boolean isEmpty() {
            return stocks.isEmpty();
        }
    }
}
