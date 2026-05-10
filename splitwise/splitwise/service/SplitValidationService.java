package splitwise.service;


import splitwise.exception.InvalidExpenseException;
import splitwise.models.Expense;
import splitwise.models.User;
import splitwise.registry.UserRegistry;

import java.util.Map;

public class SplitValidationService {



    public void ExactSplitValidation(Expense.ExpenseBuilder expenseBuilder) {
        Double amount = expenseBuilder.getAmount();
        Map<User,Double>split=expenseBuilder.getExpenseSplit();
        Double currSum=0.0;
        for(Map.Entry<User,Double> entry:split.entrySet()){
            currSum+=entry.getValue();
            if(UserRegistry.getUser(entry.getKey().getUsername())==null){
                throw new InvalidExpenseException("User " + entry.getKey().getUsername() + " does not exist");
            }
        }
        if(!currSum.equals(amount)){
            throw new InvalidExpenseException("Amount of the expense should be equal to sum of individual splits");
        }
    }

    public void PercentageSplitValidation(Expense.ExpenseBuilder expenseBuilder) {
        Map<User,Double>split=expenseBuilder.getExpenseSplit();
        Double currSum=0.0;
        for(Map.Entry<User,Double> entry:split.entrySet()){
            currSum+=entry.getValue();
            if(UserRegistry.getUser(entry.getKey().getUsername())==null){
                throw new InvalidExpenseException("User " + entry.getKey().getUsername() + " does not exist");
            }
        }
        if(!currSum.equals(100.00)){
            throw new InvalidExpenseException("Amount of the expense should be equal to 100.00");
        }
    }
}
