package models;

import enums.PlayerType;
import strategy.MoveStrategy;

public class Player {
    private final String name;
    private final char symbol;
    private final PlayerType type;
    private final MoveStrategy strategy;

    public Player(String name, char symbol, PlayerType type, MoveStrategy strategy) {
        this.name = name;
        this.symbol = symbol;
        this.type = type;
        this.strategy = strategy;
    }

    public String getName() {
        return name;
    }
    public char getSymbol() {
        return symbol;
    }

    public MoveStrategy getStrategy() {
        return strategy;
    }
}
