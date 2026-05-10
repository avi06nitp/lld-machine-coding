package splitwise.models;

import splitwise.registry.UserRegistry;

import java.util.HashMap;
import java.util.Map;

public class User {

    private final String username;
    private final String email;
    private  Map<User,Double>balance = new HashMap<>();

    private User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public static User createUser(String username, String email){
        User user= UserRegistry.getUser(username);
        if(user==null){
            user =new User(username,email);
            UserRegistry.registerUser(user);
        }
        return user;
    }

    //Getters
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public Map<User,Double> getBalance() {
        return balance;
    }


    // Setters
    public void setBalance(User user, Double value) {
      balance.put(user,balance.getOrDefault(user,balance.getOrDefault(user,0.0) + value));
    }
}
