package edu.project2;

import edu.project2.Cell.CellType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static edu.project2.Cell.CellType.PASSAGE;
import static edu.project2.Cell.CellType.WALL;

public class RecursiveBacktrackingMazeGenerator implements MazeGenerator {
    public static final int MIN_MAZE_SIZE = 3;

    @Override public Maze createMaze(int height, int width) {
        if (height < MIN_MAZE_SIZE || width < MIN_MAZE_SIZE) {
            throw new IllegalArgumentException("Sizes of maze are too small!!!");
        }
        Cell[][] mazeGrid = new Cell[height][width];
        int[][] maze = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                maze[i][j] = 0;
            }
        }

        boolean[][] visited = new boolean[height][width];
        depthFirstSearch(0, 0, height, width, visited, maze);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                CellType cellType = (maze[i][j] == 0) ? WALL : PASSAGE;
                mazeGrid[i][j] = new Cell(i, j, cellType);
            }
        }
        return new Maze(height, width, mazeGrid);
    }

    void depthFirstSearch(int x, int y, int height, int width, boolean[][] visited, int[][] maze) {
        visited[x][y] = true;
        maze[x][y] = 1;
        ArrayList<Direction> directionList = new ArrayList<>(List.of(Direction.values()));
        Collections.shuffle(directionList);
        for (var direction : directionList) {
            int newX = x + direction.dx * 2;
            int newY = y + direction.dy * 2;
            if (checkInBound(newX, height) && checkInBound(newY, width) && !visited[newX][newY]) {
                visited[x + direction.dx][y + direction.dy] = true;
                maze[x + direction.dx][y + direction.dy] = 1;
                depthFirstSearch(newX, newY, height, width, visited, maze);
            }
        }
    }

    boolean checkInBound(int a, int bound) {
        return (a >= 0) && (a < bound);
    }

    public enum Direction {

        UP(0, -1),
        DOWN(0, 1),
        RIGHT(1, 0),
        LEFT(-1, 0);

        public final int dx;
        public final int dy;

        Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }
    }
}
