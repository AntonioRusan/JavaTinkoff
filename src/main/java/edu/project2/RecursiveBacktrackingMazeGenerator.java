package edu.project2;

import edu.project2.Cell.CellType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static edu.project2.Cell.CellType.PASSAGE;
import static edu.project2.Cell.CellType.WALL;

public class RecursiveBacktrackingMazeGenerator implements MazeGenerator {
    @Override public Maze createMaze(int height, int width) {
        Cell[][] mazeGrid = new Cell[height][width];
        int[][] maze = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                maze[i][j] = 0;
            }
        }

        boolean[][] visited = new boolean[height][width];
        DepthFirstSearch(0, 0, height, width, visited, maze);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (maze[i][j] == 1) {
                    System.out.print(".");
                } else {
                    System.out.print("#");
                }
            }
            System.out.println();
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                CellType cellType = (maze[i][j] == 0) ? WALL : PASSAGE;
                mazeGrid[i][j] = new Cell(i, j, cellType);
            }
        }
        return new Maze(height, width, mazeGrid);
    }

    void DepthFirstSearch(int x, int y, int height, int width, boolean[][] visited, int[][] maze) {
        visited[x][y] = true;
        maze[x][y] = 1;
        ArrayList<Direction> directionList = new ArrayList<>(List.of(Direction.values()));
        Collections.shuffle(directionList);
        for (var direction : directionList) {
            int new_x = x + direction.dx * 2;
            int new_y = y + direction.dy * 2;
            if ((new_x >= 0 && new_x < width) && (new_y >= 0 && new_y < height) && !visited[new_x][new_y]) {
                visited[x + direction.dx][y + direction.dy] = true;
                maze[x + direction.dx][y + direction.dy] = 1;
                DepthFirstSearch(new_x, new_y, height, width, visited, maze);
            }
        }
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
