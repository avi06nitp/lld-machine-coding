package model;

import enums.PlayerType;
import strategy.PlayerStrategy;

public class Player {
    private final String name;
    private final char symbol;
    private final PlayerType playerType;
    private final PlayerStrategy playerStrategy;

    public Player(String name, char symbol, PlayerType playerType, PlayerStrategy playerStrategy) {
        this.name = name;
        this.symbol = symbol;
        this.playerType = playerType;
        this.playerStrategy = playerStrategy;
    }

    public String getName() {
        return name;
    }

    public char getSymbol() {
        return symbol;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public Move makeMove(Board board) {
        return playerStrategy.makeMove(this, board);
    }

    @Override
    public String toString() {
        return String.format("%s (%c)", name, symbol);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Player player = (Player) obj;
        return symbol == player.symbol;
    }

    @Override
    public int hashCode() {
        return Character.hashCode(symbol);
    }
}