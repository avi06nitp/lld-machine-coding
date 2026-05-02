package factory;

import enums.PlayerType;
import models.Player;
import service.GameService;
import service.InputValidationService;
import strategy.ComputerMoveStrategy;
import strategy.HumanMoveStrategy;

public class PlayerFactory {

    public  Player createPlayer(String playerName, char symbol, PlayerType type, InputValidationService inputValidationService) {
        switch (type) {
            case HUMAN -> {
                Player player=new Player(playerName,symbol,type,new HumanMoveStrategy(inputValidationService));

                return player;
            }
            case COMPUTER -> {
                Player player=new Player(playerName,symbol,type,new ComputerMoveStrategy());
                return player;
            }default ->
                throw new IllegalArgumentException("Unknown player type");
        }
    }
}
