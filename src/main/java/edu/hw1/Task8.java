package edu.hw1;

public class Task8 {
    private static final int CHESSBOARD_SIZE = 8;
    private static final int NUMBER_OF_KNIGHT_MOVES = 8;
    private static final int[][] KNIGHT_MOVES = new int[][] {
        {-2, -1},
        {-2, +1},

        {-1, +2},
        {+1, +2},

        {+2, +1},
        {+2, -1},

        {+1, -2},
        {-1, -2}
    };

    private Task8() {
    }

    public static boolean knightBoardCapture(int[][] chessBoard) throws IllegalArgumentException {
        boolean cannotBeatKnights = true;
        if (!checkBoardSize(chessBoard)) {
            throw new IllegalArgumentException("Wrong chessboard size");
        } else {
            for (int i = 0; i < CHESSBOARD_SIZE && cannotBeatKnights; i++) {
                for (int j = 0; j < CHESSBOARD_SIZE && cannotBeatKnights; j++) {
                    if (chessBoard[i][j] == 1) {
                        cannotBeatKnights = cannotBeatKnight(i, j, chessBoard);
                    }
                }
            }
        }
        return cannotBeatKnights;
    }

    private static boolean cannotBeatKnight(int x, int y, int[][] chessBoard) {
        boolean cannotBeatKnight = true;
        for (int i = 0; i < NUMBER_OF_KNIGHT_MOVES && cannotBeatKnight; i++) {
            int newX = x + KNIGHT_MOVES[i][0];
            int newY = y + KNIGHT_MOVES[i][1];
            if (coordianteOnBoard(newX) && coordianteOnBoard(newY)) {
                if (chessBoard[newX][newY] == 1) {
                    cannotBeatKnight = false;
                }
            }
        }
        return cannotBeatKnight;
    }

    private static boolean coordianteOnBoard(int coordinate) {
        return (coordinate >= 0 && coordinate <= CHESSBOARD_SIZE - 1);
    }

    private static boolean checkBoardSize(int[][] chessBoard) {
        boolean isValidSize = true;
        if (chessBoard.length != CHESSBOARD_SIZE) {
            isValidSize = false;
        } else {
            for (int[] ints : chessBoard) {
                if (ints.length != CHESSBOARD_SIZE) {
                    isValidSize = false;
                    break;
                }
            }
        }
        return isValidSize;
    }
}
