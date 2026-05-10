package splitwise.service;

import splitwise.models.User;
import splitwise.registry.UserRegistry;

import java.util.Map;

public class BalanceService {

    public void showBalance() {
       Map<String,User> users = UserRegistry.getUsers();
       for (Map.Entry<String,User> entry : users.entrySet()) {
           User user = entry.getValue();
           System.out.println("Showing balance for user " + user.getUsername());
           Map<User,Double>balance = user.getBalance();
           for (Map.Entry<User,Double> balanceEntry : balance.entrySet()) {
               User balanceUser = balanceEntry.getKey();
               Double total = balanceEntry.getValue();
               if(total<0){
                   System.out.println("User " + balanceUser.getUsername() + " will get " + total +" from " +user.getUsername());
               }
               else{
                   System.out.println("User " + balanceUser.getUsername() + " will give " + total +" from " +user.getUsername());
               }

           }
       }
    }

    public void showBalanceForUser(User user) {
        System.out.println("Showing balance for user " + user.getUsername());
        Map<User,Double>balance = user.getBalance();
        for (Map.Entry<User,Double> balanceEntry : balance.entrySet()) {
            User balanceUser = balanceEntry.getKey();
            Double total = balanceEntry.getValue();
            if(total<0){
                System.out.println("User " + balanceUser.getUsername() + " will get " + total +" from " +user.getUsername());
            }
            else{
                System.out.println("User " + balanceUser.getUsername() + " will give " + total +" from " +user.getUsername());
            }

        }
    }
}
