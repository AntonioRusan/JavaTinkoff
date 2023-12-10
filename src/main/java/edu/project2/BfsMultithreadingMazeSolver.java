package edu.project2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class BfsMultithreadingMazeSolver implements MazeSolver {
    private static final int NUM_OF_THREADS = 12;

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
            try {
                return breadthFirstSearchWithPathMultithreading(start, finish, maze.height, maze.width, maze);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public List<Coordinate> breadthFirstSearchWithPathMultithreading(
        Coordinate start,
        Coordinate finish,
        int height,
        int width,
        Maze maze
    ) throws InterruptedException {
        AtomicInteger[][] distances = new AtomicInteger[height][width];
        Queue<Coordinate> vertexQueue = new ConcurrentLinkedQueue<>();

        Map<Coordinate, Optional<Coordinate>> parentAndChildCoordinates = new ConcurrentHashMap<>();

        vertexQueue.offer(start);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                distances[i][j] = new AtomicInteger(-1);
            }
        }
        distances[start.x()][start.y()].set(0);
        parentAndChildCoordinates.put(start, Optional.empty());
        while (!vertexQueue.isEmpty()) {
            vertexQueue.parallelStream().forEach(currentVertex -> {
                    vertexQueue.poll();
                    ArrayList<RecursiveBacktrackingMazeGenerator.Direction>
                        directionList = new ArrayList<>(List.of(RecursiveBacktrackingMazeGenerator.Direction.values()));
                    for (var dir : directionList) {
                        int newX = currentVertex.x() + dir.dx;
                        int newY = currentVertex.y() + dir.dy;
                        Coordinate newVertex = new Coordinate(newX, newY);
                        if (checkCoordinate(newVertex, height, width)) {
                            Cell cellInMaze = maze.grid[newX][newY];
                            if (distances[newX][newY].get() == -1 && cellInMaze.type() != Cell.CellType.WALL) {
                                distances[newX][newY].set(distances[currentVertex.x()][currentVertex.y()].get() + 1);
                                vertexQueue.offer(newVertex);
                                parentAndChildCoordinates.put(newVertex, Optional.of(currentVertex));
                            }
                        }
                    }
                }
            );
        }
        List<Coordinate> path = new ArrayList<>();
        if (distances[finish.x()][finish.y()].get() != -1) {
            Optional<Coordinate> currentVertex = Optional.of(finish);
            while (currentVertex.isPresent()) {
                path.add(currentVertex.get());
                currentVertex = parentAndChildCoordinates.get(currentVertex.get());
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
