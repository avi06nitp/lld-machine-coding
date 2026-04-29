package service;

import enums.GameStatus;
import models.Board;

import java.util.HashMap;
import java.util.Map;


public class WinnerDetectionService {

    private final Board board;


    public WinnerDetectionService(Board board) {
        this.board = board;
    }

    public GameStatus getWinner() {
        if(checkDiagonal() || checkRowsAndCols()){
            System.out.println("Winner detected");
          return  GameStatus.FINISHED;
        }else if (checkDraw()){
            return  GameStatus.DRAW;
        }
        return GameStatus.IN_PROGRESS;
    }

    private boolean checkRowsAndCols() {
        for (int i = 0; i < board.getSize(); i++) {
            HashMap<Character, Integer> symbolCountRows = new HashMap<>();
            HashMap<Character, Integer> symbolCountCols = new HashMap<>();
            for (int j = 0; j < board.getSize(); j++) {
                if (board.getCell(i, j) != '\0') {
                    int currValue = symbolCountRows.getOrDefault(board.getCell(i, j), 0);
                    symbolCountRows.put(board.getCell(i, j), currValue + 1);
                    if (currValue + 1 == board.getSize()) return true;
                }
                if (board.getCell(j, i) != '\0') {
                    int currValueCols = symbolCountCols.getOrDefault(board.getCell(j, i), 0);
                    symbolCountCols.put(board.getCell(j, i), currValueCols + 1);
                    if (currValueCols + 1 == board.getSize()) return true;
                }
            }
        }
        return false;
    }

    private boolean checkDiagonal() {
        Map<Character, Integer> symbolCountLeftDiagonal = new HashMap<>();
        Map<Character, Integer> symbolCountRightDiagonal = new HashMap<>();

        for (int i = 0; i < board.getSize(); i++) {
            if (board.getCell(i, i) != '\0') {
                int currValueLeft = symbolCountLeftDiagonal.getOrDefault(board.getCell(i, i), 0);
                symbolCountLeftDiagonal.put(board.getCell(i, i), currValueLeft + 1);
                if (currValueLeft + 1 == board.getSize()) return true;
            }
            if (board.getCell(i, board.getSize() - 1 - i) != '\0') {
                int currValueRight = symbolCountRightDiagonal.getOrDefault(board.getCell(i, board.getSize() - 1 - i), 0);
                symbolCountRightDiagonal.put(board.getCell(i, board.getSize() - 1 - i), currValueRight + 1);
                if (currValueRight + 1 == board.getSize()) return true;
            }
        }
        return false;
    }

    private boolean checkDraw() {
        int countFilledCells = 0;
        for (int i = 0; i < board.getSize(); i++) {
          for (int j = 0; j < board.getSize(); j++) {
              if(board.getCell(i,j)!='\0'){
                  countFilledCells++;
              }
          }
        }
        return countFilledCells == board.getSize() * board.getSize();
    }

}
