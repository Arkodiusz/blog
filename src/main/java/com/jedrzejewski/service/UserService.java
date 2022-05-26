package com.jedrzejewski.service;

import com.jedrzejewski.domain.User;
import com.jedrzejewski.repository.MySqlUserRepository;
import com.jedrzejewski.repository.UserRepository;

public class UserService {
    private final UserRepository repository = new MySqlUserRepository();

    public User createUser(User newUser) {
        return repository.create(newUser);
    }

    public User login(String user, String password) {
        //TODO: implement login logic
        String passwordByUsername = repository.getPasswordByUsername(user);
        if (password.equals(passwordByUsername)) {
            return User.builder()
                    .username(user)
                    .password(password)
                    .build();
        }
        return User.builder()
                .build();
    }
}
