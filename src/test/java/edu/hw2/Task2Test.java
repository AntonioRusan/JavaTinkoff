package edu.hw2;

import edu.hw2.Task2.Rectangle;
import edu.hw2.Task2.Square;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task2Test {
    static Arguments[] rectangles() {
        return new Arguments[] {
            Arguments.of(new Rectangle()),
            Arguments.of(new Square())
        };
    }

    @ParameterizedTest
    @MethodSource("rectangles")
    void rectangleArea(Rectangle rect) {
        Rectangle newWidthRect = rect.setWidth(20);
        Rectangle newHeightRect = newWidthRect.setHeight(10);

        assertThat(newHeightRect.area()).isEqualTo(200.0);
    }

    @Test
    void rectangleInitiatedCheckArea() {
        Rectangle[] rectArray = new Rectangle[] {new Rectangle(1, 2), new Square(3)};
        for (Rectangle rect : rectArray) {
            Rectangle newWidthRect = rect.setWidth(20);
            Rectangle newHeightRect = newWidthRect.setHeight(10);
            assertThat(newHeightRect.area()).isEqualTo(200.0);
        }

    }

}
