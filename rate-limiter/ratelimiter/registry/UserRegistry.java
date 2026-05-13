package ratelimiter.registry;

import ratelimiter.models.User;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public  class UserRegistry {

   private final static Map<Integer, User> users = new HashMap<>();



    public static Map<Integer, User> getUsers() {
        return users;
    }
    public static void addUser(User user) {
        users.put(user.getId(), user);
    }
    public static void removeUser(User user) {
        users.remove(user.getId());
    }
    public static User getUser(int id) {
        return users.get(id);
    }
}
