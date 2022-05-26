package com.jedrzejewski.repository;

import com.jedrzejewski.db.H2DatabaseDriver;
import com.jedrzejewski.domain.Blog;
import org.h2.value.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MySqlBlogRepository implements BlogRepository {

    @Override
    public List<Blog> getAll() {
        String sql = "SELECT * FROM blog";
        Object result = new H2DatabaseDriver().executeStatement(sql);
        if (!(result instanceof ArrayList)) {
            throw new RuntimeException("Unexpected result from " + sql);
        }
        return (List<Blog>) result;
    }

    @Override
    public Blog create(Blog newEntry) {
        String text = newEntry.getText();
        int userid = newEntry.getUserid();
        String sql = "INSERT INTO blog(text, userid) VALUES ('test text 1', 1)";
        Object result = new H2DatabaseDriver().executeStatement(sql);
        if (!(result instanceof Blog)) {
            throw new RuntimeException("Unexpected result from " + sql);
        }
        return (Blog) result;
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM blog WHERE id=" + id;
        new H2DatabaseDriver().executeStatement(sql);
    }
}
