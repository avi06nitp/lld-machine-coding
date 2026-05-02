package tictactoe.exceptions;

public class AlreadyOccupiedCellException extends InvalidInputExeption {
    public AlreadyOccupiedCellException(int row, int column) {
        super("Provided cell at row:"+row +" column: "+column +" is already occupied");
    }
}
