package strategy;

import model.Board;
import model.Move;

public interface WinningStrategy {
    boolean checkWinner(Board board, Move lastMove);
}