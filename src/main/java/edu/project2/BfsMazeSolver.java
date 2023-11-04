package edu.project2;

import edu.project2.RecursiveBacktrackingMazeGenerator.Direction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class BfsMazeSolver implements MazeSolver {
    @Override
    public List<Coordinate> solveMaze(Maze maze, Coordinate start, Coordinate finish) {
        if (!checkCoordinate(start, maze.height, maze.width)) {
            throw new IllegalArgumentException(String.format(
                "Start cell coordinates are out of bounds: (%d %d)",
                start.x(),
                start.y()
            ));
        } else if (!checkCoordinate(finish, maze.height, maze.width)) {
            throw new IllegalArgumentException(String.format(
                "Finish cell coordinates are out of bounds: (%d %d)",
                finish.x(),
                finish.y()
            ));
        } else {
            return breadthFirstSearchWithPath(start, finish, maze.height, maze.width, maze);
        }
    }

    public List<Coordinate> breadthFirstSearchWithPath(
        Coordinate start,
        Coordinate finish,
        int height,
        int width,
        Maze maze
    ) {
        int[][] distances = new int[height][width];
        Queue<Coordinate> vertexQueue = new LinkedList<>();

        Map<Coordinate, Coordinate> parentAndChildCoordinates = new HashMap<>();

        vertexQueue.offer(start);

        for (int[] row : distances) {
            Arrays.fill(row, -1);
        }
        distances[start.x()][start.y()] = 0;
        parentAndChildCoordinates.put(start, null);
        while (!vertexQueue.isEmpty()) {
            Coordinate currentVertex = vertexQueue.poll();
            ArrayList<Direction> directionList = new ArrayList<>(List.of(Direction.values()));
            for (var dir : directionList) {
                int newX = currentVertex.x() + dir.dx;
                int newY = currentVertex.y() + dir.dy;
                Coordinate newVertex = new Coordinate(newX, newY);
                if (checkCoordinate(newVertex, height, width)) {
                    Cell cellInMaze = maze.grid[newX][newY];
                    if (distances[newX][newY] == -1 && cellInMaze.type() != Cell.CellType.WALL) {
                        distances[newX][newY] = distances[currentVertex.x()][currentVertex.y()] + 1;
                        vertexQueue.offer(newVertex);
                        parentAndChildCoordinates.put(newVertex, currentVertex);
                    }
                }
            }
        }
        List<Coordinate> path = new ArrayList<>();
        if (distances[finish.x()][finish.y()] != -1) {
            Coordinate currentVertex = finish;
            while (currentVertex != null) {
                path.add(currentVertex);
                currentVertex = parentAndChildCoordinates.get(currentVertex);
            }
        } else {
            throw new IllegalArgumentException(String.format(
                "No path for this finish cell: (%d %d)",
                finish.x(),
                finish.y()
            ));
        }
        Collections.reverse(path);
        return path;
    }

    boolean checkCoordinate(Coordinate cord, int height, int width) {
        return checkInBound(cord.x(), height) && checkInBound(cord.y(), width);
    }

    boolean checkInBound(int a, int bound) {
        return (a >= 0) && (a < bound);
    }
}
