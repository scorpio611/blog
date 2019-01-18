package com.ngocnd.controller;

import com.ngocnd.model.Blog;
import com.ngocnd.model.Category;
import com.ngocnd.service.BlogService;
import com.ngocnd.service.CategoryService;
import com.ngocnd.service.impl.BlogServiceImpl;
import com.ngocnd.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class BlogController {
    @Autowired
    BlogService blogService = new BlogServiceImpl();

    @Autowired
    CategoryService categoryService = new CategoryServiceImpl();

    @GetMapping("/blog/create")
    public ModelAndView createBlog() {
        ModelAndView modelAndView = new ModelAndView("/blog/create");
        modelAndView.addObject("blog", new Blog());
        return modelAndView;
    }

    @PostMapping("/blog/create")
    public ModelAndView saveBlog(@ModelAttribute("blog") Blog blog) {
        blogService.save(blog);
        ModelAndView modelAndView = new ModelAndView("/blog/create");
        modelAndView.addObject("blog", new Blog());
        modelAndView.addObject("message", "New blog created successfully");
        return modelAndView;
    }

    @GetMapping("/")
    public ModelAndView Blogs(@RequestParam("search") Optional<String> search, Pageable pageable) {
        Page<Blog> blogs;
        if (search.isPresent()) {
            blogs = blogService.findAllByTilte(search.get(), pageable);
        } else {

            blogs = blogService.findAll(new PageRequest(pageable.getPageNumber(), 5));
        }
        ModelAndView modelAndView = new ModelAndView("/blog/view");
        modelAndView.addObject("blogs", blogs);
        return modelAndView;
    }

    @GetMapping("/blog/edit/{id}")
    public ModelAndView editBlog(@PathVariable Long id) {
        Blog blog = blogService.findById(id);
        if (blog != null) {
            ModelAndView modelAndView = new ModelAndView("/blog/edit");
            modelAndView.addObject("message", "Edit blog successfully");
            modelAndView.addObject("blog", blog);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/blog/edit")
    public String updateBlog(@ModelAttribute("blog") Blog blog) {
        blogService.save(blog);
        return "redirect:/";
    }


    @GetMapping("/blog/delete/{id}")
    public ModelAndView deleteBlog(@PathVariable Long id) {
        Blog blog = blogService.findById(id);
        if (id != null) {
            ModelAndView modelAndView = new ModelAndView("/blog/delete");
            modelAndView.addObject("message", "Delete blog successfully");
            modelAndView.addObject("blog", blog);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/blog/delete")
    public String deleteBlog(@ModelAttribute("blog") Blog blog) {
        blogService.remove(blog.getId());
        return "redirect:/";
    }

    @ModelAttribute("categories")
    public Iterable<Category> categories() {
        return categoryService.findAll();
    }
}
