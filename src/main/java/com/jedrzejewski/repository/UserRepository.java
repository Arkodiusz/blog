package com.jedrzejewski.repository;

import com.jedrzejewski.domain.User;

public interface UserRepository {
    boolean create(User newUser);
    User getUserByUsernameAndPassword(String username, String password);
}
