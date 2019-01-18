package com.ngocnd.service;

import com.ngocnd.model.Blog;
import com.ngocnd.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {
    Page<Blog> findAll(Pageable pageable);

    Blog findById(Long id);

    void save(Blog blog);

    void remove(Long id);

    Iterable<Blog> findAllByCategory(Category category);

    Page<Blog> findAllByTilte(String title, Pageable pageable);

}
