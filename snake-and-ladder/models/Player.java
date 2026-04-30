package models;

import strategy.DiceRollStrategy;

public class Player {
    private final String name;
    private int position;
    private final DiceRollStrategy diceRollStrategy;

    public Player(String name, DiceRollStrategy diceRollStrategy) {
        this.name = name;
        this.position = 1;
        this.diceRollStrategy = diceRollStrategy;
    }

    //Getters and Setters
    public String getName() {
        return name;
    }
    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }
    public DiceRollStrategy getDiceRollStrategy() {
        return diceRollStrategy;
    }
}
