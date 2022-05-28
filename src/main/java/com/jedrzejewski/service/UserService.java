package com.jedrzejewski.service;

import com.jedrzejewski.domain.User;
import com.jedrzejewski.repository.H2dbUserRepository;
import com.jedrzejewski.repository.UserRepository;

public class UserService {
    private final UserRepository repository = new H2dbUserRepository();

    public boolean createUser(User newUser) {
        return repository.create(newUser);
    }

    public User login(String user, String password) {
        User userByUsernameAndPassword = repository.getUserByUsernameAndPassword(user, password);
        return userByUsernameAndPassword;
    }
}
