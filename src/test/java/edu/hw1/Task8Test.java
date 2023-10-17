package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task8Test {
    @Test
    @DisplayName("Конь не может захватить другого коня")
    void knightsCanCaptureEachOther() {
        // given
        int[][] chessBoard = new int[][] {
            {0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 1, 0, 1, 0},
            {0, 1, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 1, 0, 0, 0}
        };

        // when
        boolean canCapture = Task8.knightBoardCapture(chessBoard);

        // then
        assertThat(canCapture).isTrue();
    }

    @Test
    @DisplayName("Конь может захватить другого коня")
    void knightsCannotCaptureEachOther() {
        // given
        int[][] chessBoard = new int[][] {
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1},
            {0, 0, 0, 0, 1, 0, 1, 0},
            {0, 0, 1, 0, 0, 1, 0, 1},
            {1, 0, 0, 0, 1, 0, 1, 0},
            {0, 0, 0, 0, 0, 1, 0, 1},
            {1, 0, 0, 0, 1, 0, 1, 0},
            {0, 0, 0, 1, 0, 1, 0, 1}
        };

        // when
        boolean canCapture = Task8.knightBoardCapture(chessBoard);

        // then
        assertThat(canCapture).isFalse();
    }

    @Test
    @DisplayName("Конь может захватить другого коня другой вариант")
    void knightsCannotCaptureEachOtherAnother() {
        // given
        int[][] chessBoard = new int[][] {
            {0, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0}
        };

        // when
        boolean canCapture = Task8.knightBoardCapture(chessBoard);

        // then
        assertThat(canCapture).isFalse();
    }

    @Test
    @DisplayName("Доска неправильного размера (не хватает столбцов)")
    void chessIsNotValid() {
        // given
        int[][] chessBoard = new int[][] {
            {0, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 1, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0}
        };

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> Task8.knightBoardCapture(chessBoard));

        String expectedMessage = "Wrong chessboard size";
        String actualMessage = exception.getMessage();

        // then
        assertThat(expectedMessage).isEqualTo(actualMessage);
    }

    @Test
    @DisplayName("Доска неправильного размера (не хватает строк)")
    void chessIsNotValidWrongRows() {
        // given
        int[][] chessBoard = new int[][] {
            {0, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0},
        };

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> Task8.knightBoardCapture(chessBoard));

        String expectedMessage = "Wrong chessboard size";
        String actualMessage = exception.getMessage();

        // then
        assertThat(expectedMessage).isEqualTo(actualMessage);
    }
}
