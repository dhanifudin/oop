package id.ac.polinema.repository;

import id.ac.polinema.model.User;

public interface UserRepository {
    User findByUsername(String username);
}
