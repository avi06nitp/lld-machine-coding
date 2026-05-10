package splitwise.strategy;

import splitwise.models.Expense;
import splitwise.models.User;

import java.util.Map;

public class PercentageSplitStrategy implements SplitStrategy {
    @Override
    public void split(Expense expense) {
        // Get Spender and Total Amount
        User spender=expense.getSpender();
        Double amount=expense.getAmount();

        Map<User,Double> participants=expense.getSplits();
        Map<User,Double>spenderBalance=spender.getBalance();

        for(Map.Entry<User,Double> entry:participants.entrySet()){
            if(!entry.getKey().equals(spender)){
                Map<User,Double >participantBalance=entry.getKey().getBalance();

                //Update Participant's and Spender's Balances
                Double getSpenderBalanceOnParticipant=participantBalance.getOrDefault(spender,0.0);
                Double getParticiplantBalanceOnSpender=spenderBalance.getOrDefault(entry.getKey(),0.0);

                participantBalance.put(spender,getSpenderBalanceOnParticipant-entry.getValue()*amount/100);
                spenderBalance.put(entry.getKey(),getParticiplantBalanceOnSpender+entry.getValue()*amount/100);

            }
        }

    }
}
