package service;

import exceptions.AlreadyOccupiedCellException;
import exceptions.OutOfBoundsExceptions;
import models.Board;

public class InputValidationService {

    public boolean validateInput(int rows, int columns, Board board) {
        if (rows < 0 || columns < 0 || rows>= board.getSize() || columns >= board.getSize()) {
        throw new OutOfBoundsExceptions(rows, columns);
        }else if(board.getCell(rows,columns)!='\0') {
            throw new AlreadyOccupiedCellException(rows, columns);
        }
        return true;
    }
}
