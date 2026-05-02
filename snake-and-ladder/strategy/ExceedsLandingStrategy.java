package strategy;

import models.Board;
import models.Player;

public class ExceedsLandingStrategy implements WinningStrategy {


    @Override
    public boolean playerWon(Player currPlayer, Board board) {
        int position = currPlayer.getPosition();
        if(position >= board.getSize()){
            return true;
        }
        return false;
    }

    @Override
    public void setPlayerPosition(Player currPlayer, int finalPosition, Board board) {
            currPlayer.setPosition(finalPosition);
    }
}
