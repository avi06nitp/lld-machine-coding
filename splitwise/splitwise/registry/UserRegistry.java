package splitwise.registry;

import splitwise.models.User;

import java.util.HashMap;
import java.util.Map;

public class UserRegistry {
    public static Map<String, User> users=new HashMap<String, User>();


    public static void registerUser(User user){
        users.put(user.getUsername(), user);
    }
    public static User getUser(String username){
        return users.get(username);
    }
    public static Map<String, User> getUsers() {
        return Map.copyOf(users);
    }
}
