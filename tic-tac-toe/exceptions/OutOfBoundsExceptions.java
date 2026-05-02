package tictactoe.exceptions;

public class OutOfBoundsExceptions extends InvalidInputExeption {
    public OutOfBoundsExceptions(int row, int column) {
        super("Provided position at row:"+row +" column: "+column +" is out of bounds");
    }
}
