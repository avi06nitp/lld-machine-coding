package splitwise.models;

import splitwise.enums.SplitType;
import splitwise.strategy.EqualSplitStrategy;
import splitwise.strategy.ExactSplitStrategy;
import splitwise.strategy.PercentageSplitStrategy;
import splitwise.strategy.SplitStrategy;

import java.util.Map;

public class Expense {

    private static int idCounter=0;
    private final int id;
    private final User spender;
    private final Double amount;
    private final Map<User,Double> splits;
    private final SplitStrategy strategy;

    private Expense( User spender, Double amount, Map<User, Double> splits, SplitStrategy strategy) {
        idCounter++;
        this.id = idCounter;
        this.spender = spender;
        this.amount = amount;
        this.splits = splits;
        this.strategy = strategy;
    }

    // Factory to create expense
    public static Expense createExpense(User spender, Double amount, Map<User, Double> splits, SplitType splitType) {
        switch(splitType) {
            case PERCENTAGE->{
                return new Expense(spender,amount,splits,new PercentageSplitStrategy());
            }case EXACT -> {
                return new Expense(spender,amount,splits,new ExactSplitStrategy());
            }
            case EQUAL -> {
                return new Expense(spender,amount,splits,new EqualSplitStrategy());
            }
            default -> {
                throw new IllegalArgumentException("Unsupported split type: " + splitType);
            }
        }

    }

    // Getters
    public int getId() {
        return id;
    }
    public User getSpender() {
        return spender;
    }
    public Double getAmount() {
        return amount;
    }
    public Map<User, Double> getSplits() {
        return splits;
    }
    public SplitStrategy getStrategy() {
        return strategy;
    }


}
