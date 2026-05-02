package tictactoe.factory;

import tictactoe.enums.PlayerType;
import tictactoe.models.Player;
import tictactoe.service.GameService;
import tictactoe.service.InputValidationService;
import tictactoe.strategy.ComputerMoveStrategy;
import tictactoe.strategy.HumanMoveStrategy;

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
