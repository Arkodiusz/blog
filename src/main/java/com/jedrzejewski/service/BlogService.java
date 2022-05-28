package com.jedrzejewski.service;

import com.jedrzejewski.domain.Blog;
import com.jedrzejewski.repository.BlogRepository;
import com.jedrzejewski.repository.H2dbBlogRepository;

import java.util.List;

public class BlogService {
    private final BlogRepository repository = new H2dbBlogRepository();

    public List<Blog> fetchAllEntries() {
        return repository.getAll();
    }

    public boolean createEntry(Blog newEntry)  {
        return repository.create(newEntry);
    }

    public void deleteEntryById(Integer id) {
        repository.deleteById(id);
    }
}
