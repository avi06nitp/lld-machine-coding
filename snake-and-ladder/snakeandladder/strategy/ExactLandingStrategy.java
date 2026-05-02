package snakeandladder.strategy;

import snakeandladder.models.Board;
import snakeandladder.models.Player;

public class ExactLandingStrategy implements WinningStrategy   {


    @Override
    public boolean playerWon(Player currPlayer, Board board) {
        if(currPlayer.getPosition()== board.getSize()){
            return true;
        }
        return false;

    }

    @Override
    public void setPlayerPosition(Player currPlayer, int finalPosition, Board board) {
        if(finalPosition <= board.getSize()){
            currPlayer.setPosition(finalPosition);
            System.out.println(currPlayer.getPosition());
        }
    }
}
