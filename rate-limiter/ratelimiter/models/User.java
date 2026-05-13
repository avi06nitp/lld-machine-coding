package ratelimiter.models;

import ratelimiter.registry.UserRegistry;

public class User {

    private static int idCounter = 0;
    private final int id;
    private final String name;

    private User(String name) {
        this.id = idCounter++;
        this.name = name;

    }

    // Getters & Setters
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public static User createUser(String name) {
        if(UserRegistry.getUsers().containsKey(name)) {
            return UserRegistry.getUsers().get(name);
        }
        User user = new User(name);
        UserRegistry.addUser(user);
        return user;
    }


}
