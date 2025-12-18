package com.example.demo.service;

import com.example.demo.entity.Category;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {
    Category createCategory(Category category);
    Category updateCategory(Long id, Category category);
    Category getCategoryById(Long id);
    List<Category> getAllCategories();
    void deactivateCategory(Long id);
}
