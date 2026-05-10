package splitwise.strategy;

import splitwise.models.Expense;

public interface ExpenseSplitStrategy {

    void splitExpense(Expense expense);

}
