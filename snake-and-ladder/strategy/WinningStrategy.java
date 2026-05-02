package strategy;

import models.Board;
import models.Player;

public interface WinningStrategy {
    boolean playerWon(Player currPlayer, Board board);
    void setPlayerPosition(Player currPlayer, int finalPosition, Board board);
}
