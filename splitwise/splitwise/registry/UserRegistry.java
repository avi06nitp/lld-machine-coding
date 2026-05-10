package splitwise.registry;

import splitwise.models.User;

import java.util.HashMap;
import java.util.Map;

public class UserRegistry {
    private static Map<String, User> users = new HashMap<>();

    public static void registerUser(User user) {
        users.put(user.getUsername(), user);
    }

    public static void unregisterUser(User user) {
        users.remove(user.getUsername());
    }

    public static User getUser(String username) {
        return users.get(username);
    }

    public static Map<String, User> getUsers() {
        return users;
    }

}
