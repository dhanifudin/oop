package id.ac.polinema.repository;

import id.ac.polinema.PasswordHasher;
import id.ac.polinema.model.User;
import java.util.HashMap;
import java.util.Map;

public class InMemoryUserRepository implements UserRepository {
    private Map<String, User> users = new HashMap<>();

    public InMemoryUserRepository() {
        users.put("teller1", new User("teller1", PasswordHasher.hash("teller123")));
    }

    @Override
    public User findByUsername(String username) {
        return users.get(username);
    }
}
