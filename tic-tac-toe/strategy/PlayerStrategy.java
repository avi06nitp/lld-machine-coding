package strategy;

import model.Board;
import model.Move;
import model.Player;

public interface PlayerStrategy {
    Move makeMove(Player player, Board board);
}