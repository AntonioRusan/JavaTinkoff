package edu.project2;

public final class Maze {
    public final int height;
    public final int width;
    public final Cell[][] grid;

    public Maze(int height, int width, Cell[][] grid) {
        this.height = height;
        this.width = width;
        this.grid = grid;
    }
}
