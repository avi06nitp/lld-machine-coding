package service;

import exceptions.InvalidBoardSize;

public class BoardValidation {

   public boolean validBoard(int size) {

       if (size < 3 || size > 9) {
          throw new InvalidBoardSize("Invalid board size: "+size);
       }
       return true;

   }

}
