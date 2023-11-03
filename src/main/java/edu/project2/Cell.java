package edu.project2;

public record Cell(int row, int col, CellType type) {
    public enum CellType { WALL, PASSAGE }
}
