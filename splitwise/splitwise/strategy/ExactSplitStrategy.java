package splitwise.strategy;

import splitwise.exception.InvalidExpenseException;
import splitwise.models.Expense;
import splitwise.models.User;

import java.util.Map;

public class ExactSplitStrategy implements ExpenseSplitStrategy {

    @Override
    public void splitExpense(Expense expense) {
        Map<User, Double> shares = expense.getExpenseSplit();
        double sum = 0.0;
        for (Double v : shares.values()) {
            sum += v;
        }
        if (Math.abs(sum - expense.getAmount()) > 1e-9) {
            throw new InvalidExpenseException("Sum of exact splits (" + sum + ") must equal expense amount (" + expense.getAmount() + ")");
        }

        User spender = expense.getSepnder();
        for (Map.Entry<User, Double> entry : shares.entrySet()) {
            User participant = entry.getKey();
            if (participant.equals(spender)) {
                continue;
            }
            double share = entry.getValue();
            spender.getBalance().merge(participant, share, Double::sum);
            participant.getBalance().merge(spender, -share, Double::sum);
        }
    }
}