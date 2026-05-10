package splitwise.service;

import splitwise.models.User;
import splitwise.registry.UserRegistry;

import java.util.Map;

public class BalanceServiceService {

    public void printBalanceForUser(User user) {
        System.out.println("Balance for " + user.getUsername());
        Map<User,Double> balances=user.getBalance();
        for(Map.Entry<User,Double> entry:balances.entrySet()) {
            System.out.println(entry.getKey().getUsername()+": "+entry.getValue());
        }
    }

    public void printBlanaceForAllUsers() {
        System.out.println("Balanace for all users");

       Map<String,User>users= UserRegistry.getUsers();

       for(Map.Entry<String,User> user:users.entrySet()) {
           System.out.println("Showing Balance for Username: "+user.getValue().getUsername());
           Map<User,Double> balances=user.getValue().getBalance();
           for(Map.Entry<User,Double> entry:balances.entrySet()) {
               System.out.println(entry.getKey().getUsername()+": "+entry.getValue());
           }
       }
    }

    void simplifyBalance(Map<User,Double> balance) {}
}
