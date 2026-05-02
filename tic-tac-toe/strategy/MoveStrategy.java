package strategy;

import models.Board;
import models.Player;

public interface MoveStrategy {

    void move(Board board, Player player);
}
