package edu.project2;

import java.util.List;

public interface MazeSolver {
    List<Coordinate> solveMaze(Maze maze, Coordinate start, Coordinate finish);
}
