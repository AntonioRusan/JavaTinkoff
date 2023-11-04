package edu.project2;

import edu.project2.Cell.CellType;
import java.util.HashMap;
import java.util.List;

public class ConsoleMazePainter implements MazePainter {
    @Override
    public void showMaze(Maze maze) {
        String[][] mazePainting = initMazeDraw(maze);
        printMaze(mazePainting);
    }

    @Override
    public void showPathInMaze(Maze maze, List<Coordinate> path) {
        String[][] mazePainting = initMazeDraw(maze);
        for (var item : path) {
            mazePainting[item.x() + 1][item.y() + 1] = CELL_IMAGE.get(CellType.WAY);
        }
        printMaze(mazePainting);
    }

    public static String[][] initMazeDraw(Maze maze) {
        String[][] mazePainting = new String[maze.height + 2][maze.width + 2];
        for (int i = 1; i < maze.height + 1; i++) {
            mazePainting[i][0] = "|";
            mazePainting[i][maze.width + 1] = "|";
        }
        for (int j = 1; j < maze.width + 1; j++) {
            mazePainting[0][j] = "__";
            mazePainting[maze.height + 1][j] = "‾‾";
        }
        for (int i = 0; i < maze.height; i++) {
            for (int j = 0; j < maze.width; j++) {
                mazePainting[i + 1][j + 1] = CELL_IMAGE.get(maze.grid[i][j].type());
            }
        }
        mazePainting[0][0] = "_";
        mazePainting[maze.height + 1][maze.width + 1] = "‾";
        mazePainting[maze.height + 1][0] = "‾";
        mazePainting[0][maze.width + 1] = "_";
        return mazePainting;
    }

    public void printMaze(String[][] maze) {
        for (String[] strings : maze) {
            for (String string : strings) {
                System.out.print(string);
            }
            System.out.println();
        }
    }

    public static final HashMap<CellType, String> CELL_IMAGE = new HashMap<>() {{
        put(CellType.WALL, "██");
        put(CellType.PASSAGE, "  ");
        put(CellType.WAY, "**");
    }};
}
