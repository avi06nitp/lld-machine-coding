package tictactoe.strategy;

import tictactoe.models.Board;
import tictactoe.models.Player;

public interface MoveStrategy {

    void move(Board board, Player player);
}
