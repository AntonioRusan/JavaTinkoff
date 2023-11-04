package edu.project2;

import java.util.List;

public interface MazePainter {
    void showMaze(Maze maze);

    void showPathInMaze(Maze maze, List<Coordinate> path);
}
