package models;

public class Board {
    private final int size;
    private char[][] board;

    public Board(int size) {
        this.size = size;
        board = new char[size][size];
    }

    public int getSize() {
        return size;
    }

    public char getCell(int x, int y) {
        return board[x][y];
    }
    public void setCell(int x, int y, char symbol) {
        board[x][y] = symbol;
    }
   public void printBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
   }
}
