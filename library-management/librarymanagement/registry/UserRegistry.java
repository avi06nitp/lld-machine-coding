package librarymanagement.registry;

import librarymanagement.models.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserRegistry {

    public static Map<Integer, User>users=new HashMap();

    public static int getUserById(int id) {
        return users.get(id).getId();
    }

    public  static Optional<User> getUserByEmail(String email) {
       for(User user:users.values()) {
           if(user.getEmail().equals(email)) {
               return Optional.of(user);
           }
       }
       return Optional.empty();
    }

    public static void registerUser(User user) {
        users.put(user.getId(), user);
    }


}
