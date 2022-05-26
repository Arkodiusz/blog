package com.jedrzejewski.repository;

import com.jedrzejewski.domain.Blog;

import java.util.List;
import java.util.Optional;

public class MySqlBlogRepository implements BlogRepository {

    @Override
    public List<Optional<Blog>> getAll() {
        return null;
    }

    @Override
    public Blog create(Blog newEntry) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        return;
    }
}
