package tictactoe.models;


public class Move {
    private final int row;
    private final int col;
    private final Board board;

    //Constructor
    public Move(int row, int col, Board board) {
        this.row = row;
        this.col = col;
        this.board = board;

    }

    //Getters
    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }


}
