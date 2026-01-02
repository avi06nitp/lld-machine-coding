package model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int size;
    private final Cell[][] cells;
    private int filledCells;

    public Board(int size) {
        this.size = size;
        this.cells = new Cell[size][size];
        this.filledCells = 0;
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
    }

    public int getSize() {
        return size;
    }

    public Cell getCell(int row, int col) {
        if (!isValidPosition(row, col)) {
            return null;
        }
        return cells[row][col];
    }

    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    public boolean isCellEmpty(int row, int col) {
        Cell cell = getCell(row, col);
        return cell != null && cell.isEmpty();
    }

    public void makeMove(Move move) {
        Cell cell = move.getCell();
        cell.setSymbol(move.getPlayer().getSymbol());
        filledCells++;
    }

    public boolean isFull() {
        return filledCells == size * size;
    }

    public List<Cell> getEmptyCells() {
        List<Cell> emptyCells = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (cells[i][j].isEmpty()) {
                    emptyCells.add(cells[i][j]);
                }
            }
        }
        return emptyCells;
    }

    public void reset() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j].reset();
            }
        }
        filledCells = 0;
    }

    public void display() {
        System.out.println("\n   " + createColumnHeaders());
        System.out.println("  " + createHorizontalLine());

        for (int i = 0; i < size; i++) {
            System.out.print(i + " |");
            for (int j = 0; j < size; j++) {
                System.out.print(" " + cells[i][j] + " |");
            }
            System.out.println();
            System.out.println("  " + createHorizontalLine());
        }
        System.out.println();
    }

    private String createColumnHeaders() {
        StringBuilder header = new StringBuilder();
        for (int i = 0; i < size; i++) {
            header.append(i).append("   ");
        }
        return header.toString();
    }

    private String createHorizontalLine() {
        return "---".repeat(size) + "-".repeat(size + 1);
    }

    public Cell[][] getCells() {
        return cells;
    }
}