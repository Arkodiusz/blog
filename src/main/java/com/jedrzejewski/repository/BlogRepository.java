
package com.jedrzejewski.repository;

import com.jedrzejewski.domain.Blog;

import java.util.List;

public interface BlogRepository {
    List<Blog> getAll();
    Blog create(Blog newEntry);
    void deleteById(Integer id);
}
