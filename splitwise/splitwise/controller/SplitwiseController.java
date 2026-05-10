package splitwise.controller;


import splitwise.enums.ExpenseType;
import splitwise.exception.InvalidExpenseException;
import splitwise.models.Expense;
import splitwise.models.User;
import splitwise.registry.UserRegistry;
import splitwise.service.BalanceService;
import splitwise.strategy.EqualSplitStrategy;
import splitwise.strategy.ExactSplitStrategy;
import splitwise.strategy.ExpenseSplitStrategy;
import splitwise.strategy.PercentageSplitStrategy;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SplitwiseController {

    private final BalanceService balanceService = new BalanceService();
    private final Scanner scanner;

    public SplitwiseController(Scanner scanner) {
        this.scanner = scanner;
    }

    public void initialize() {
        System.out.println("Please enter no. of users you want to add!");
        int numberofUser = scanner.nextInt();
        for (int i = 0; i < numberofUser; i++) {
            createUser();
        }
        System.out.println("Please enter no. of expenses you want to add!");
        int numberofExpenses = scanner.nextInt();
        for (int i = 0; i < numberofExpenses; i++) {
            createExpense();
        }
        balanceService.showBalance();
    }

    private void createUser() {
        System.out.println("Please enter user name!");
        String userName = scanner.next();
        System.out.println("Please enter email address!");
        String email = scanner.next();
        User.createUser(userName, email);
        System.out.println("User successfully created!");
    }

    private void createExpense() {
        System.out.println("Please enter username of spender!");
        String spenderName = scanner.next();
        User spender = UserRegistry.getUser(spenderName);
        if (spender == null) {
            System.out.println("Spender " + spenderName + " does not exist. Skipping expense.");
            return;
        }

        System.out.println("Please enter expense amount!");
        double expenseAmount = scanner.nextDouble();

        System.out.println("Please enter total no. of people where it will be shared, including spender if he is part of expense!");
        int totalPeople = scanner.nextInt();

        System.out.println("Please enter the expense type (EQUAL/EXACT/PERCENTAGE)");
        ExpenseType expenseType;
        try {
            expenseType = ExpenseType.valueOf(scanner.next().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid expense type!");
            return;
        }

        Map<User, Double> splitInput = collectSplitInput(expenseType, totalPeople);
        if (splitInput.isEmpty()) {
            System.out.println("No valid participants. Skipping expense.");
            return;
        }

        ExpenseSplitStrategy strategy = createStrategy(expenseType);

        Expense expense = new Expense.ExpenseBuilder()
                .id()
                .amount(expenseAmount)
                .type(expenseType)
                .strategy(strategy)
                .split(splitInput)
                .spender(spender)
                .build();

        try {
            strategy.splitExpense(expense);
            System.out.println("Expense recorded.");
        } catch (InvalidExpenseException e) {
            System.out.println(e.getMessage());
        }
    }

    private Map<User, Double> collectSplitInput(ExpenseType type, int totalPeople) {
        Map<User, Double> map = new HashMap<>();
        for (int i = 0; i < totalPeople; i++) {
            System.out.println("Please enter username of person to share expense!");
            String name = scanner.next();
            User u = UserRegistry.getUser(name);
            if (u == null) {
                System.out.println("User " + name + " does not exist. Skipping.");
                if (type == ExpenseType.PERCENTAGE || type == ExpenseType.EXACT) {
                    scanner.nextDouble();
                }
                continue;
            }

            double value = 0.0;
            if (type == ExpenseType.PERCENTAGE) {
                System.out.println("Please enter split percentage");
                value = scanner.nextDouble();
            } else if (type == ExpenseType.EXACT) {
                System.out.println("Please enter split amount");
                value = scanner.nextDouble();
            }
            map.put(u, value);
        }
        return map;
    }

    private ExpenseSplitStrategy createStrategy(ExpenseType type) {
        return switch (type) {
            case EQUAL -> new EqualSplitStrategy();
            case PERCENTAGE -> new PercentageSplitStrategy();
            case EXACT -> new ExactSplitStrategy();
        };
    }
}