package com.jedrzejewski.service;

import com.jedrzejewski.domain.Blog;
import com.jedrzejewski.repository.BlogRepository;
import com.jedrzejewski.repository.MySqlBlogRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BlogService {
    private final BlogRepository repository = new MySqlBlogRepository();

    public List<Blog> fetchAllEntries() {
        return repository.getAll().stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public Blog createEntry(Blog newEntry)  {
        return repository.create(newEntry);
    }

    public void deleteEntryById(Integer id) {
        repository.deleteById(id);
    }
}