package com.jedrzejewski.repository;

import com.jedrzejewski.domain.User;

public interface UserRepository {
    User create(User newUser);
    String getPasswordByUsername(String username);
}
