package com.jedrzejewski.service;

import com.jedrzejewski.domain.User;
import com.jedrzejewski.repository.H2dbUserRepository;
import com.jedrzejewski.repository.UserRepository;


/**
 * Class introduced to separate http logic from database logic and to make room for some business logic (e.g. validation)
 */
public class UserService {
    private final UserRepository repository = new H2dbUserRepository();

    /**
     * Calls repository to create new entry in 'user' table
     *
     * @param  newUser       object to be saved
     * @return               boolean value if saving was successful
     */
    public boolean createUser(User newUser) {
        return repository.create(newUser);
    }

    /**
     * Calls repository to validate user credentials
     *
     * @param user              username to be tested
     * @param password          password to be tested
     * @return                  found user (or null if user not found)
     */
    public User login(String user, String password) {
        return repository.getUserByUsernameAndPassword(user, password);
    }
}
