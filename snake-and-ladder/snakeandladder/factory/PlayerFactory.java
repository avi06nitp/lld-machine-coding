package snakeandladder.factory;

import snakeandladder.enums.PlayerType;
import snakeandladder.models.Player;
import snakeandladder.strategy.FairDiceRollStrategy;
import snakeandladder.strategy.SpecialDiceRollStrategy;

public class PlayerFactory {

    public Player createPlayer(String name, PlayerType type) {
        switch (type) {
            case HUMAN:{
                return new Player(name,new FairDiceRollStrategy());
            }case COMPUTER:{
                return new Player(name, new SpecialDiceRollStrategy());
            }
            default:{
                throw new IllegalArgumentException("Invalid player type");
            }
        }

    }
}
