package edu.hw3;

import edu.hw3.Task6.MyStockMarket;
import edu.hw3.Task6.Stock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task6Test {

    @Test
    @DisplayName("Тест самая дорогая акция")
    void getMostValuableStockTest() {
        // given
        MyStockMarket market = new MyStockMarket();
        market.add(new Stock(5));
        market.add(new Stock(1));
        market.add(new Stock(10));
        market.add(new Stock(2));
        Stock expected = new Stock(10);
        // when
        Stock result = market.mostValuableStock();
        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Тест добавления акции")
    void addStockTest() {
        // given
        MyStockMarket market = new MyStockMarket();
        // when
        market.add(new Stock(1));
        market.add(new Stock(5));
        market.add(new Stock(1));
        // then
        assertThat(market.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("Тест удаления акции")
    void removeStockTest() {
        // given
        MyStockMarket market = new MyStockMarket();
        market.add(new Stock(1));
        market.add(new Stock(5));
        market.add(new Stock(1));
        // when
        market.remove(new Stock(5));
        market.remove(new Stock(1));
        market.remove(new Stock(1));
        // then
        assertThat(market.isEmpty()).isTrue();
    }

}
