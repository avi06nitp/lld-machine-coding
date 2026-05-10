package splitwise.strategy;

import splitwise.models.Expense;


public interface SplitStrategy {

    void split(Expense expense);
}
