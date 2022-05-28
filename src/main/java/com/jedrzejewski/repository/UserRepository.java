package com.jedrzejewski.repository;

import com.jedrzejewski.domain.User;

/**
 * Interface that contains methods to communicate with 'blog' table
 */
public interface UserRepository {

    /**
     * Saving new user in db
     *
     * @param  newUser       object to be saved
     * @return               boolean value if saving was successful
     */
    boolean create(User newUser);

    /**
     * Validating user credentials
     *
     * @param username          username to be tested
     * @param password          password to be tested
     * @return                  found user (or null if user not found)
     */
    User getUserByUsernameAndPassword(String username, String password);
}
