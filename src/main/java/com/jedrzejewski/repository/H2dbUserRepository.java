package com.jedrzejewski.repository;

import com.jedrzejewski.domain.Blog;
import com.jedrzejewski.domain.User;
import lombok.NonNull;
import org.h2.value.Value;

import java.util.ArrayList;
import java.util.List;

import static com.jedrzejewski.BlogApplication.h2DbDriver;


/**
 * Implementation of interface for communication with 'user' table in H2 database
 */
public class H2dbUserRepository implements UserRepository {

    /**
     * Calling db driver to make SQL query in order to create new user in 'user' table
     *
     * @param  newUser      object to be saved
     * @return               boolean value if saving was successful
     */
    @Override
    public boolean create(User newUser) {
        String username = newUser.getUsername();
        @NonNull String password = newUser.getPassword();
        @NonNull String permission = newUser.getPermission();
        @NonNull String readonly = newUser.getReadonly();
        String sql = "INSERT INTO `user`(username, password, permission, readonly) " +
                "VALUES ('" + username + "', '" + password + "', '" + permission + "', '" + readonly + "')";
        Object result = h2DbDriver.executeStatement(sql);
        if (!(result instanceof Boolean)) {
            throw new RuntimeException("Unexpected result from " + sql);
        }
        return true;
    }

    /**
     * Calling db driver to make SQL query in order to
     * validate username and password combination new entry in 'blog' table
     *
     * @param username          username to be tested
     * @param password          password to be tested
     * @return                  found user (or null if user not found)
     */
    public User getUserByUsernameAndPassword(String username, String password) {
        String sql = "SELECT * FROM `user` WHERE " +
                "`username` = \'" +username+ "\' AND `password` = \'" + password + "\'";
        Object statementResult = h2DbDriver.executeStatement(sql);
        if (!(statementResult instanceof ArrayList<?>) || (((ArrayList<?>) statementResult).size() != 1)) {
            return null;
        }
        List<User> result = new ArrayList<>();
        for (Object o : (List<Blog>) statementResult) {
            Value[] values = (Value[]) o;
            result.add(User.builder()
                            .userid(values[0].getInt())
                            .username(values[1].getString())
                            .password(values[2].getString())
                            .permission(values[3].getString())
                            .readonly(values[4].getString())
                            .build());
        }
        return result.get(0);
    }
}
