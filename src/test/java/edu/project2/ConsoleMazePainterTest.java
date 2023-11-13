package edu.project2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.project2.Cell.CellType.PASSAGE;
import static edu.project2.Cell.CellType.WALL;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConsoleMazePainterTest {
    private static Maze getSampleMaze() {
        int height = 4;
        int width = 4;
        int[][] tempIntGrid = {
            {1, 1, 1, 0},
            {0, 0, 1, 1},
            {1, 1, 1, 0},
            {0, 0, 1, 1},
        };
        Cell[][] mazeGrid = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Cell.CellType cellType = (tempIntGrid[i][j] == 0) ? WALL : PASSAGE;
                mazeGrid[i][j] = new Cell(i, j, cellType);
            }
        }
        return new Maze(height, width, mazeGrid);
    }

    @Test
    @DisplayName("Проверка, что правильно выводится лабиринт")
    void checkShowMazePainterTest() {
        // given
        Maze maze = getSampleMaze();
        MazePainter mazePainter = new ConsoleMazePainter();
        String[][] expectedResult = {
            {"_", "__", "__", "__", "__", "_"},
            {"|", "  ", "  ", "  ", "██", "|"},
            {"|", "██", "██", "  ", "  ", "|"},
            {"|", "  ", "  ", "  ", "██", "|"},
            {"|", "██", "██", "  ", "  ", "|"},
            {"‾", "‾‾", "‾‾", "‾‾", "‾‾", "‾"},
        };
        // when
        String[][] actualPainting = mazePainter.initMazeDraw(maze);

        // then
        assertTrue(Arrays.deepEquals(actualPainting, expectedResult));
    }

    @Test
    @DisplayName("Проверка, что правильно выводится лабиринт c путём из точки (0,0) в (3,3)")
    void checkShowPathInMazePainterTest() {
        // given
        Maze maze = getSampleMaze();
        MazePainter mazePainter = new ConsoleMazePainter();
        String[][] expectedResult = {
            {"_", "__", "__", "__", "__", "_"},
            {"|", "**", "**", "**", "██", "|"},
            {"|", "██", "██", "**", "  ", "|"},
            {"|", "  ", "  ", "**", "██", "|"},
            {"|", "██", "██", "**", "**", "|"},
            {"‾", "‾‾", "‾‾", "‾‾", "‾‾", "‾"},
        };
        List<Coordinate> path = new ArrayList<>() {{
            add(new Coordinate(0, 0));
            add(new Coordinate(0, 1));
            add(new Coordinate(0, 2));
            add(new Coordinate(1, 2));
            add(new Coordinate(2, 2));
            add(new Coordinate(3, 2));
            add(new Coordinate(3, 3));
        }};
        // when
        String[][] actualPainting = mazePainter.initMazeDrawWithPath(maze, path);

        // then
        assertTrue(Arrays.deepEquals(actualPainting, expectedResult));
    }
}
