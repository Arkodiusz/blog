package com.jedrzejewski.repository;

import com.jedrzejewski.domain.Blog;
import org.h2.value.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.jedrzejewski.BlogApplication.h2DbDriver;


/**
 * Implementation of interface for communication with 'blog' table in H2 database
 */
public class H2dbBlogRepository implements BlogRepository {

    /**
     * Calling db driver to make SQL query in order to get all entris form 'blog' table
     * and processing query result.
     *
     * @return              list of found elements
     */
    @Override
    public List<Blog> getAll() {
        String sql = "SELECT * FROM `blog`";
        Object statementResult = h2DbDriver.executeStatement(sql);
        if (!(statementResult instanceof ArrayList)) {
            throw new RuntimeException("Unexpected result from " + sql);
        }
        List<Blog> result = new ArrayList<>();
        for (Object o : (List<Blog>) statementResult) {
            Value[] values = (Value[]) o;
            result.add(Blog.builder()
                            .id(values[0].getInt())
                            .text(values[1].getString())
                            .userid(values[2].getInt())
                            .build());
        }
        return result;
    }

    /**
     * Calling db driver to make SQL query in order to create new entry in 'blog' table
     *
     * @param  newEntry      object to be saved
     * @return               boolean value if saving was successful
     */
    @Override
    public boolean create(Blog newEntry) {
        String text = newEntry.getText();
        int userid = newEntry.getUserid();
        String sql = "INSERT INTO `blog`(text, userid) VALUES ('" + text + "', " + userid + ")";
        Object result = h2DbDriver.executeStatement(sql);
        if (!(result instanceof Boolean)) {
           throw new RuntimeException("Unexpected result from " + sql);
        }
        return true;
    }

    /**
     * Calling db driver to make SQL query in order to delete entry with given id
     *
     * @param id            id of object selected for deletion
     */
    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM `blog` WHERE id=" + id;
        h2DbDriver.executeStatement(sql);
    }
}
