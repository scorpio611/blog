package com.ngocnd.controller;


import com.ngocnd.model.Blog;
import com.ngocnd.model.Category;
import com.ngocnd.service.BlogService;
import com.ngocnd.service.CategoryService;
import com.ngocnd.service.impl.BlogServiceImpl;
import com.ngocnd.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CategoryController {
    @Autowired
    CategoryService categoryService = new CategoryServiceImpl();

    @Autowired
    BlogService blogService = new BlogServiceImpl();


    @GetMapping("/categories")
    public ModelAndView listCategories() {
        Iterable<Category> categories = categoryService.findAll();
        ModelAndView modelAndView = new ModelAndView("/category/index");
        modelAndView.addObject("categories", categories);
        return modelAndView;
    }

    @GetMapping("/category/create")
    public ModelAndView createCategory() {
        ModelAndView modelAndView = new ModelAndView("/category/create");
        modelAndView.addObject("category", new Category());
        return modelAndView;
    }

    @PostMapping("/category/create")
    public ModelAndView saveCategory(@ModelAttribute("category") Category category) {
        categoryService.save(category);

        ModelAndView modelAndView = new ModelAndView("/category/create");
        modelAndView.addObject("category", new Category());
        modelAndView.addObject("message", "New category created successfully");
        return modelAndView;
    }

    @GetMapping("/category/edit/{id}")
    public ModelAndView editCategory(@PathVariable Long id) {
        Category category = categoryService.findById(id);
        if (category != null) {
            ModelAndView modelAndView = new ModelAndView("/category/edit");
            modelAndView.addObject("category", category);
            return modelAndView;

        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/category/edit")
    public ModelAndView updateProvince(@ModelAttribute("category") Category category) {
        categoryService.save(category);
        ModelAndView modelAndView = new ModelAndView("/category/edit");
        modelAndView.addObject("category", category);
        modelAndView.addObject("message", "Category updated successfully");
        return modelAndView;
    }

    @GetMapping("/category/delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        Category category = categoryService.findById(id);
        if (category != null) {
            ModelAndView modelAndView = new ModelAndView("/category/delete");
            modelAndView.addObject("category", category);
            return modelAndView;

        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/category/delete")
    public String deleteProvince(@ModelAttribute("category") Category category) {
        categoryService.remove(category.getId());
        return "redirect:categories";
    }

    @GetMapping("/category/view/{id}")
    public ModelAndView viewProvince(@PathVariable("id") Long id) {
        Category category = categoryService.findById(id);
        if (category == null) {
            return new ModelAndView("/error.404");
        }

        Iterable<Blog> customers = blogService.findAllByCategory(category);

        ModelAndView modelAndView = new ModelAndView("/category/view");
        modelAndView.addObject("categories", category);
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }
}
