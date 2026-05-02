package tictactoe.factory;

import tictactoe.enums.PlayerType;
import tictactoe.models.Player;
import tictactoe.service.InputValidationService;
import tictactoe.strategy.ComputerMoveStrategy;
import tictactoe.strategy.HumanMoveStrategy;

public class PlayerFactory {

    public Player createPlayer(String playerName, char symbol, PlayerType type, InputValidationService inputValidationService, char opponentSymbol) {
        switch (type) {
            case HUMAN -> {
                return new Player(playerName, symbol, type, new HumanMoveStrategy(inputValidationService));
            }
            case COMPUTER -> {
                return new Player(playerName, symbol, type, new ComputerMoveStrategy(opponentSymbol));
            }
            default -> throw new IllegalArgumentException("Unknown player type");
        }
    }
}
