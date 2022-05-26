package com.jedrzejewski.repository;

import com.jedrzejewski.domain.User;

public class MySqlUserRepository implements UserRepository {

    @Override
    public User create(User newUser) {
        return null;
    }

    @Override
    public String getPasswordByUsername(String username) {
        return null;
    }
}
