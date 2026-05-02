package snakeandladder.strategy;

public class FairDiceRollStrategy implements DiceRollStrategy {

    @Override
    public int roll() {
        return  (int) (Math.random() * 6) + 1;
    }
}
