package splitwise.strategy;

import splitwise.models.Expense;
import splitwise.models.User;

import java.util.Map;

public class EqualSplitStrategy implements ExpenseSplitStrategy {

    @Override
    public void splitExpense(Expense expense) {
        User spender = expense.getSepnder();
        Map<User, Double> participants = expense.getExpenseSplit();
        if (participants.isEmpty()) {
            return;
        }
        double share = expense.getAmount() / participants.size();

        for (User participant : participants.keySet()) {
            if (participant.equals(spender)) {
                continue;
            }
            spender.getBalance().merge(participant, share, Double::sum);
            participant.getBalance().merge(spender, -share, Double::sum);
        }
    }
}