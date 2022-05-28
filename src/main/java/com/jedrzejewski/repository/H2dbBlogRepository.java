package com.jedrzejewski.repository;

import com.jedrzejewski.domain.Blog;
import org.h2.value.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.jedrzejewski.BlogApplication.h2DbDriver;

public class H2dbBlogRepository implements BlogRepository {

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

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM `blog` WHERE id=" + id;
        h2DbDriver.executeStatement(sql);
    }
}
