package edu.hw2;

import edu.hw2.Task1.Expr.Addition;
import edu.hw2.Task1.Expr.Constant;
import edu.hw2.Task1.Expr.Exponent;
import edu.hw2.Task1.Expr.Multiplication;
import edu.hw2.Task1.Expr.Negate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task1Test {
    @Test
    @DisplayName("Проверка константы")
    void checkConstant() {
        // given
        double number = 13.7;

        //when
        var thirteen = new Constant(number);

        // then
        assertThat(thirteen.evaluate()).isEqualTo(number);
    }

    @Test
    @DisplayName("Проверка отрицания")
    void checkNegate() {
        // given
        double inputNumber = 999.5;

        // when
        var negative = new Negate(new Constant(inputNumber));

        // then
        assertThat(negative.evaluate()).isEqualTo(-inputNumber);
    }

    @Test
    @DisplayName("Проверка сложения")
    void checkAddition() {
        // given
        double firstNumber = 1.5;
        double secondNumber = 3.5;

        // when
        var add = new Addition(new Constant(firstNumber), new Constant(secondNumber));

        // then
        assertThat(add.evaluate()).isEqualTo(firstNumber + secondNumber);
    }

    @Test
    @DisplayName("Проверка умножения")
    void checkMultiplication() {
        // given
        double firstNumber = 1.5;
        double secondNumber = 4;

        // when
        var mult = new Multiplication(new Constant(firstNumber), new Constant(secondNumber));

        // then
        assertThat(mult.evaluate()).isEqualTo(firstNumber * secondNumber);
    }

    @Test
    @DisplayName("Проверка возведения в степень")
    void checkExponent() {
        // given
        double base = 5;
        double pow = 3;

        // when
        var exp = new Exponent(new Constant(base), pow);

        // then
        assertThat(exp.evaluate()).isEqualTo(Math.pow(base, pow));
    }

    @Test
    @DisplayName("Проверка сложного выражения")
    void checkComposite() {
        // given
        double expectedResult = Math.pow((2 + 4) * (-1), 2) + 1;

        // when
        var two = new Constant(2);
        var four = new Constant(4);
        var negOne = new Negate(new Constant(1));
        var sumTwoFour = new Addition(two, four);
        var mult = new Multiplication(sumTwoFour, negOne);
        var exp = new Exponent(mult, 2);
        var result = new Addition(exp, new Constant(1));

        // then
        assertThat(result.evaluate()).isEqualTo(expectedResult);
    }
}
