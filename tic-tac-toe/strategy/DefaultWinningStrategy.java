package strategy;

import model.Board;
import model.Cell;
import model.Move;

public class DefaultWinningStrategy implements WinningStrategy {

    @Override
    public boolean checkWinner(Board board, Move lastMove) {
        if (lastMove == null) {
            return false;
        }

        Cell cell = lastMove.getCell();
        char symbol = lastMove.getPlayer().getSymbol();

        return checkRow(board, cell.getRow(), symbol)
                || checkColumn(board, cell.getCol(), symbol)
                || checkDiagonals(board, cell, symbol);
    }

    private boolean checkRow(Board board, int row, char symbol) {
        int size = board.getSize();
        for (int col = 0; col < size; col++) {
            Cell cell = board.getCell(row, col);
            if (cell.isEmpty() || cell.getSymbol() != symbol) {
                return false;
            }
        }
        return true;
    }

    private boolean checkColumn(Board board, int col, char symbol) {
        int size = board.getSize();
        for (int row = 0; row < size; row++) {
            Cell cell = board.getCell(row, col);
            if (cell.isEmpty() || cell.getSymbol() != symbol) {
                return false;
            }
        }
        return true;
    }

    private boolean checkDiagonals(Board board, Cell lastCell, char symbol) {
        int size = board.getSize();
        int row = lastCell.getRow();
        int col = lastCell.getCol();

        if (row == col) {
            if (checkMainDiagonal(board, symbol)) {
                return true;
            }
        }

        if (row + col == size - 1) {
            if (checkAntiDiagonal(board, symbol)) {
                return true;
            }
        }

        return false;
    }

    private boolean checkMainDiagonal(Board board, char symbol) {
        int size = board.getSize();
        for (int i = 0; i < size; i++) {
            Cell cell = board.getCell(i, i);
            if (cell.isEmpty() || cell.getSymbol() != symbol) {
                return false;
            }
        }
        return true;
    }

    private boolean checkAntiDiagonal(Board board, char symbol) {
        int size = board.getSize();
        for (int i = 0; i < size; i++) {
            Cell cell = board.getCell(i, size - 1 - i);
            if (cell.isEmpty() || cell.getSymbol() != symbol) {
                return false;
            }
        }
        return true;
    }
}