package com.jedrzejewski.repository;

import com.jedrzejewski.db.H2DatabaseDriver;
import com.jedrzejewski.domain.Blog;

import java.util.ArrayList;
import java.util.List;

import static com.jedrzejewski.BlogApplication.h2DbDriver;

public class MySqlBlogRepository implements BlogRepository {

    @Override
    public List<Blog> getAll() {
        String sql = "SELECT * FROM blog";
        Object result = h2DbDriver.executeStatement(sql);
        if (!(result instanceof ArrayList)) {
            throw new RuntimeException("Unexpected result from " + sql);
        }
        return (List<Blog>) result;
    }

    @Override
    public Blog create(Blog newEntry) {
        String text = newEntry.getText();
        int userid = newEntry.getUserid();
        String sql = "INSERT INTO blog(text, userid) VALUES ('" + text + "', " + userid + ")";
        Object result = h2DbDriver.executeStatement(sql);
        if (!(result instanceof Blog)) {
            throw new RuntimeException("Unexpected result from " + sql);
        }
        return (Blog) result;
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM blog WHERE id=" + id;
        h2DbDriver.executeStatement(sql);
    }
}
