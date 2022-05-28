package com.jedrzejewski.service;

import com.jedrzejewski.domain.Blog;
import com.jedrzejewski.repository.BlogRepository;
import com.jedrzejewski.repository.H2dbBlogRepository;

import java.util.List;

/**
 * Class introduced to separate http logic from database logic and to make room for some business logic (e.g. validation)
 */
public class BlogService {
    private final BlogRepository repository = new H2dbBlogRepository();

    /**
     * Calls repository to fetch all objects from 'blog' table
     *
     * @return              list of found elements
     */
    public List<Blog> fetchAllEntries() {
        return repository.getAll();
    }

    /**
     * Calls repository to create new entry in 'blog' table
     *
     * @param  newEntry      object to be saved
     * @return               boolean value if saving was successful
     */
    public boolean createEntry(Blog newEntry)  {
        return repository.create(newEntry);
    }

    /**
     * Calls repository to delete object from db by ID
     *
     * @param id            id of object selected for deletion
     */
    public void deleteEntryById(Integer id) {
        repository.deleteById(id);
    }
}
