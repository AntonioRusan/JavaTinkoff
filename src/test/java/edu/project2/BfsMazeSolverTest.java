package edu.project2;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.project2.Cell.CellType.PASSAGE;
import static edu.project2.Cell.CellType.WALL;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BfsMazeSolverTest {

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
    @DisplayName("Проверка, что найден путь в лабиринте 4x4 из точки (0,0) в (3,3)")
    void foundPathMazeSolverTest() {
        // given
        Maze maze = getSampleMaze();
        Coordinate start = new Coordinate(0, 0);
        Coordinate finish = new Coordinate(3, 3);
        MazeSolver mzSolver = new BfsMazeSolver();

        List<Coordinate> expectedPath = new ArrayList<>() {{
            add(new Coordinate(0, 0));
            add(new Coordinate(0, 1));
            add(new Coordinate(0, 2));
            add(new Coordinate(1, 2));
            add(new Coordinate(2, 2));
            add(new Coordinate(3, 2));
            add(new Coordinate(3, 3));
        }};

        // when
        List<Coordinate> actualPath = mzSolver.solveMaze(maze, start, finish);

        // then
        assertThat(actualPath).isEqualTo(expectedPath);
    }

    @Test
    @DisplayName("Проверка, что не найден путь в лабиринте 4x4 из точки (0,0) в (3,1)")
    void notFoundPathMazeSolverTest() {
        // given
        Maze maze = getSampleMaze();
        Coordinate start = new Coordinate(0, 0);
        Coordinate finish = new Coordinate(3, 1);
        MazeSolver mzSolver = new BfsMazeSolver();

        // when
        Exception exception =
            assertThrows(IllegalArgumentException.class, () -> mzSolver.solveMaze(maze, start, finish));

        String expectedMessage = String.format(
            "No path for this finish cell: (%d %d)",
            finish.x(),
            finish.y()
        );
        String actualMessage = exception.getMessage();

        // then
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    @Test
    @DisplayName("Проверка, что в лабиринте 4x4 финишная точка из (0,0) в (4,1) за границами лабиринта")
    void finishPointOutOfBoundsPathMazeSolverTest() {
        // given
        Maze maze = getSampleMaze();
        Coordinate start = new Coordinate(0, 0);
        Coordinate finish = new Coordinate(4, 1);
        MazeSolver mzSolver = new BfsMazeSolver();

        // when
        Exception exception =
            assertThrows(IllegalArgumentException.class, () -> mzSolver.solveMaze(maze, start, finish));
        String expectedMessage = String.format(
            "Finish cell coordinates are out of bounds: (%d %d)",
            finish.x(),
            finish.y()
        );
        String actualMessage = exception.getMessage();
        // then
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    @Test
    @DisplayName("Проверка, что в лабиринте 4x4 точки (4,4) в (5,5) за границами лабиринта")
    void startPointOutOfBoundsPathMazeSolverTest() {
        // given
        Maze maze = getSampleMaze();
        Coordinate start = new Coordinate(4, 4);
        Coordinate finish = new Coordinate(5, 5);
        MazeSolver mzSolver = new BfsMazeSolver();

        // when
        Exception exception =
            assertThrows(IllegalArgumentException.class, () -> mzSolver.solveMaze(maze, start, finish));
        String expectedMessage = String.format(
            "Start cell coordinates are out of bounds: (%d %d)",
            start.x(),
            start.y()
        );
        String actualMessage = exception.getMessage();
        // then
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }
}
