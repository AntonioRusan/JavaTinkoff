package edu.project2;

public final class Main {

    private Main() {
    }

    public static void main(String[] args) {
        int height = 4;
        int width = 4;
        MazeGenerator myGen = new RecursiveBacktrackingMazeGenerator();
        Maze maze = myGen.createMaze(height, width);
        MazePainter mzPainter = new ConsoleMazePainter();
        mzPainter.showMaze(maze);

        /*int[][] tempIntGrid = {
            { 1, 1, 1, 0 },
            { 0, 0, 1, 1 },
            { 0, 1, 1, 0 },
            { 1, 1, 1, 1 },
        };
        Cell[][] mazeGrid = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Cell.CellType cellType = (tempIntGrid[i][j] == 0) ? WALL : PASSAGE;
                mazeGrid[i][j] = new Cell(i, j, cellType);
            }
        }
        Maze maze = new Maze(height, width, mazeGrid);
        MazeSolver mzSolver = new BfsMazeSolver();
        Coordinate start = new Coordinate(0, 0);
        Coordinate finish = new Coordinate(0, 0);
        List<Coordinate> ans = mzSolver.solveMaze(maze, start, finish);
        for (var item: ans) {
            System.out.println(item.x() + " " + item.y());
        }

        MazePainter mzPainter = new ConsoleMazePainter();
        mzPainter.showMaze(maze);
        System.out.println();
        mzPainter.showPathInMaze(maze, ans);*/
    }
}
