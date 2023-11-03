package edu.project2;

public final class Main {

    private Main() {
    }

    public static void main(String[] args) {
        int height = 10;
        int width = 10;
        RecursiveBacktrackingMazeGenerator myGen = new RecursiveBacktrackingMazeGenerator();
        Maze gg = myGen.createMaze(height, width);
    }
}
