package edu.project2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RecursiveBacktrackingMazeGeneratorTest {
    @ParameterizedTest
    @CsvSource(value = {
        "0; 0",
        "1; 1",
        "2; 1",
        "2; 2",
        "-1; 10"
    }, delimiterString = ";")
    @DisplayName("Проверка, что нельзя создать лабиринт с такими параметрами")
    void cannotCreateMazeTest(int height, int width) {
        // given
        MazeGenerator mazeGenerator = new RecursiveBacktrackingMazeGenerator();
        // when
        Exception exception =
            assertThrows(IllegalArgumentException.class, () -> mazeGenerator.createMaze(height, width));

        String expectedMessage = "Sizes of maze are too small!!!";
        String actualMessage = exception.getMessage();

        // then
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    @ParameterizedTest
    @CsvSource(value = {
        "4; 4",
        "10; 10",
        "100; 100",
        "3; 7",
        "9; 20"
    }, delimiterString = ";")
    @DisplayName("Проверка, что создаётся лабиринт с такими параметрами")
    void canCreateMazeTest(int height, int width) {
        // given
        MazeGenerator mazeGenerator = new RecursiveBacktrackingMazeGenerator();

        // when
        Maze createdMaze = mazeGenerator.createMaze(height, width);

        // then
        assertThat(createdMaze.height).isEqualTo(height);
        assertThat(createdMaze.width).isEqualTo(width);
    }
}
