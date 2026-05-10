package splitwise.strategy;

import splitwise.exception.InvalidExpenseException;
import splitwise.models.Expense;
import splitwise.models.User;

import java.util.Map;

public class PercentageSplitStrategy implements ExpenseSplitStrategy {

    @Override
    public void splitExpense(Expense expense) {
        Map<User, Double> percentages = expense.getExpenseSplit();
        double totalPct = 0.0;
        for (Double v : percentages.values()) {
            totalPct += v;
        }
        if (Math.abs(totalPct - 100.0) > 1e-9) {
            throw new InvalidExpenseException("Sum of percentages (" + totalPct + ") must equal 100");
        }

        User spender = expense.getSepnder();
        double amount = expense.getAmount();
        for (Map.Entry<User, Double> entry : percentages.entrySet()) {
            User participant = entry.getKey();
            if (participant.equals(spender)) {
                continue;
            }
            double share = amount * entry.getValue() / 100.0;
            spender.getBalance().merge(participant, share, Double::sum);
            participant.getBalance().merge(spender, -share, Double::sum);
        }
    }
}