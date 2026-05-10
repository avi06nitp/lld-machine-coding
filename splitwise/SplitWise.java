import splitwise.enums.SplitType;
import splitwise.models.Expense;
import splitwise.models.User;
import splitwise.service.BalanceServiceService;

import java.util.HashMap;
import java.util.Map;

public class SplitWise {
    public static void main(String[] args) {

        User avi = User.create("Avi", "avi@example.com");
        User dhriti = User.create("Dhriti", "dhriti@example.com");
        User rohan = User.create("Rohan", "rohan@example.com");

        Map<User, Double> all = new HashMap<>();
        all.put(avi, 0.0);
        all.put(dhriti, 0.0);
        all.put(rohan, 0.0);

        // Avi's expenses
        runExpense(avi, 900.0, all, SplitType.EQUAL, "Dinner");
        runExpense(avi, 600.0, all, SplitType.EQUAL, "Cab to airport");
        runExpense(avi, 500.0, exact(avi, 200.0, dhriti, 150.0, rohan, 150.0), SplitType.EXACT, "Groceries");
        runExpense(avi, 1000.0, percent(avi, 20.0, dhriti, 50.0, rohan, 30.0), SplitType.PERCENTAGE, "Hotel booking");

        // Dhriti's expenses
        runExpense(dhriti, 240.0, exact(avi, 80.0, dhriti, 80.0, rohan, 80.0), SplitType.EXACT, "Coffee run");
        runExpense(dhriti, 450.0, all, SplitType.EQUAL, "Lunch");
        runExpense(dhriti, 600.0, percent(avi, 40.0, dhriti, 20.0, rohan, 40.0), SplitType.PERCENTAGE, "Books");

        // Rohan's expenses
        runExpense(rohan, 1500.0, all, SplitType.EQUAL, "Concert tickets");
        runExpense(rohan, 750.0, exact(avi, 300.0, dhriti, 200.0, rohan, 250.0), SplitType.EXACT, "Petrol");
        runExpense(rohan, 400.0, percent(avi, 25.0, dhriti, 25.0, rohan, 50.0), SplitType.PERCENTAGE, "Pizza night");

        System.out.println("\n========= FINAL BALANCES =========");
        BalanceServiceService balanceService = new BalanceServiceService();
        balanceService.printBlanaceForAllUsers();
    }

    private static void runExpense(User spender, Double amount, Map<User, Double> participants, SplitType type, String label) {
        Expense expense = Expense.createExpense(spender, amount, participants, type);
        expense.getStrategy().split(expense);
        System.out.println("Expense #" + expense.getId() + " [" + label + "] | " + type + " | Spender: " + spender.getUsername() + " | Amount: " + amount);
    }

    private static Map<User, Double> exact(User u1, Double a1, User u2, Double a2, User u3, Double a3) {
        Map<User, Double> m = new HashMap<>();
        m.put(u1, a1);
        m.put(u2, a2);
        m.put(u3, a3);
        return m;
    }

    private static Map<User, Double> percent(User u1, Double p1, User u2, Double p2, User u3, Double p3) {
        Map<User, Double> m = new HashMap<>();
        m.put(u1, p1);
        m.put(u2, p2);
        m.put(u3, p3);
        return m;
    }
}