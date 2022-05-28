
package com.jedrzejewski.repository;

import com.jedrzejewski.domain.Blog;

import java.util.List;

/**
 * Interface that contains methods to communicate with 'blog' table
 */
public interface BlogRepository {

    /**
     * Fetching all objects from 'blog' table
     *
     * @return              list of found elements
     */
    List<Blog> getAll();

    /**
     * Saving new blog entry in db
     *
     * @param  newEntry      object to be saved
     * @return               boolean value if saving was successful
     */
    boolean create(Blog newEntry);

    /**
     * Deleting object from db by ID
     *
     * @param id            id of object selected for deletion
     */
    void deleteById(Integer id);
}
