// UserManagement.java

import java.util.HashMap;
import java.util.Map;

public class UserManagement {
    private Map<String, User> users;

    public UserManagement() {
        this.users = new HashMap<>();
        // Pre-register the staff user
        users.put("staff", new User("staff", "staff123", UserType.STAFF));
        // Pre-register default users
        users.put("user1", new User("user1", "user1", UserType.USER));
        users.put("user2", new User("user2", "user2", UserType.USER));
        users.put("user3", new User("user3", "user3", UserType.USER));
        users.put("user4", new User("user4", "user4", UserType.USER));
        users.put("user5", new User("user5", "user5", UserType.USER));
    }

    public void registerUser(String username, String password, UserType userType) {
        if (!users.containsKey(username)) {
            users.put(username, new User(username, password, userType));
            System.out.println("User registered successfully!");
        } else {
            System.out.println("Username already exists. Please choose another username.");
        }
    }

    public User loginUser(String username, String password) {
        if (users.containsKey(username)) {
            User user = users.get(username);
            if (user.getPassword().equals(password)) {
                System.out.println("Login successful!");
                return user;
            }
        }
        System.out.println("Invalid username or password. Please try again.");
        return null;
    }
}
