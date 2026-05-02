package snakeandladder.strategy;

import snakeandladder.models.Board;
import snakeandladder.models.Player;

public interface WinningStrategy {
    boolean playerWon(Player currPlayer, Board board);
    void setPlayerPosition(Player currPlayer, int finalPosition, Board board);
}
