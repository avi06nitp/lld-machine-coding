package tictactoe.models;

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
        System.out.println();
        System.out.print("\033[2m     ");
        for (int j = 0; j < size; j++) {
            System.out.printf("%-4d", j);
        }
        System.out.println("\033[0m");

        System.out.print("\033[1;34m   ┌");
        for (int j = 0; j < size; j++) {
            System.out.print("───");
            System.out.print(j < size - 1 ? "┬" : "┐");
        }
        System.out.println("\033[0m");

        for (int i = 0; i < size; i++) {
            System.out.printf("\033[2m %2d \033[0m\033[1;34m│\033[0m", i);
            for (int j = 0; j < size; j++) {
                char cell = board[i][j];
                if (cell == '\0') {
                    System.out.print("\033[2m · \033[0m");
                } else {
                    System.out.print("\033[1;32m " + cell + " \033[0m");
                }
                System.out.print("\033[1;34m│\033[0m");
            }
            System.out.println();

            if (i < size - 1) {
                System.out.print("\033[1;34m   ├");
                for (int j = 0; j < size; j++) {
                    System.out.print("───");
                    System.out.print(j < size - 1 ? "┼" : "┤");
                }
                System.out.println("\033[0m");
            }
        }

        System.out.print("\033[1;34m   └");
        for (int j = 0; j < size; j++) {
            System.out.print("───");
            System.out.print(j < size - 1 ? "┴" : "┘");
        }
        System.out.println("\033[0m");
   }
}
