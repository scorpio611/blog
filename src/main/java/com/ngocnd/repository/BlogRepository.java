package com.ngocnd.repository;

import com.ngocnd.model.Blog;
import com.ngocnd.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BlogRepository extends PagingAndSortingRepository<Blog, Long> {
    Iterable<Blog> findAllByCategory(Category category);

    Page<Blog> findAllByTitle(String title, Pageable pageable);
}
