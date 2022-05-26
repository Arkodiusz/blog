package com.jedrzejewski.repository;

import com.jedrzejewski.domain.Blog;

import java.util.List;

public class MySqlBlogRepository implements BlogRepository {

    @Override
    public List<Blog> getAll() {
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
