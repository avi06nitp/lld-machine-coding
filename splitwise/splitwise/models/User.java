package splitwise.models;

import splitwise.registry.UserRegistry;

import java.util.HashMap;
import java.util.Map;

public class User {

    private static int idCounter=0;
    private final int id;
    private final String username;
    private final String email;
    private Map<User,Double> balance=new HashMap<User,Double>();

    private User(String username, String email) {
        idCounter++;
        this.id = idCounter;
        this.username = username;
        this.email = email;
    }

    public static User create(String username, String email) {
        User user= new User(username, email);
        UserRegistry.registerUser(user);
        return user;
    }

    // Getters
    public int getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public Map<User,Double> getBalance() {
        return balance;
    }

}
