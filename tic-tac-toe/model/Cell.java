package model;

import enums.CellState;

public class Cell {
    private final int row;
    private final int col;
    private CellState state;
    private Character symbol;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.state = CellState.EMPTY;
        this.symbol = null;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public CellState getState() {
        return state;
    }

    public Character getSymbol() {
        return symbol;
    }

    public void setSymbol(Character symbol) {
        this.symbol = symbol;
        this.state = CellState.FILLED;
    }

    public boolean isEmpty() {
        return state == CellState.EMPTY;
    }

    public void reset() {
        this.symbol = null;
        this.state = CellState.EMPTY;
    }

    @Override
    public String toString() {
        return symbol != null ? symbol.toString() : " ";
    }
}