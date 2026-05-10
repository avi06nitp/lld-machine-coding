package splitwise.models;

import splitwise.enums.ExpenseType;
import splitwise.strategy.ExpenseSplitStrategy;

import java.util.Map;

public class Expense {

    private final long id;
    private final User sepnder;
    private final double amount;
    private final ExpenseType type;
    private final Map<User,Double> expenseSplit;
    private final ExpenseSplitStrategy strategy;

    public Expense(ExpenseBuilder expense) {
        this.id = expense.id;
        this.amount = expense.amount;
        this.type = expense.type;
        this.expenseSplit = expense.expenseSplit;
        this.strategy = expense.strategy;
        this.sepnder=expense.spender;
    }

    //Getters
    public long getId() {
        return id;
    }
    public double getAmount() {
        return amount;
    }
    public ExpenseType getType() {
        return type;
    }
    public Map<User, Double> getExpenseSplit() {
        return expenseSplit;
    }
    public ExpenseSplitStrategy getStrategy() {
        return strategy;
    }

    public User getSepnder() {
        return sepnder;
    }

    // Builder Class
    public static class ExpenseBuilder{
        private static Integer idCounter = 0;
        private long id;
        private double amount;
        private ExpenseType type;
        private Map<User,Double> expenseSplit;
        private  ExpenseSplitStrategy strategy;
        private User spender;

        public ExpenseBuilder id() {
            idCounter++;
            this.id = idCounter;
            return this;
        }

        public ExpenseBuilder amount(double amount) {
            this.amount = amount;
            return this;
        }
        public ExpenseBuilder type(ExpenseType type) {
            this.type = type;
            return this;
        }
        public ExpenseBuilder split(Map<User,Double> expenseSplit) {
            this.expenseSplit = expenseSplit;
            return this;
        }
        public ExpenseBuilder strategy(ExpenseSplitStrategy strategy) {
            this.strategy = strategy;
            return this;
        }
        public ExpenseBuilder spender(User spender) {
            this.spender=spender;
            return this;
        }

        //Getters
        public long getId() {
            return id;
        }
        public double getAmount() {
            return amount;
        }
        public ExpenseType getType() {
            return type;
        }
        public Map<User,Double> getExpenseSplit() {
            return expenseSplit;
        }
        public ExpenseSplitStrategy getStrategy() {
            return strategy;
        }
        public User getSpender() {
            return spender;
        }

        public Expense build() {
            return new Expense(this);
        }
    }
}
