package com.example.demo.controller;

import com.example.demo.entity.Category;
import com.example.demo.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // ===================== REST ENDPOINTS =====================

    @PostMapping
    public ResponseEntity<Category> create(@RequestBody Category category) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoryService.createCategory(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(
            @PathVariable Long id,
            @RequestBody Category category
    ) {
        return ResponseEntity.ok(
                categoryService.updateCategory(id, category)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> get(@PathVariable Long id) {
        return ResponseEntity.ok(
                categoryService.getCategoryById(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<Category>> list() {
        return ResponseEntity.ok(
                categoryService.getAllCategories()
        );
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        categoryService.deactivateCategory(id);
        return ResponseEntity.ok().build();
    }

    // ===================== TEST-EXPECTED METHODS =====================
    // 🔥 REQUIRED FOR PLATFORM / JUNIT TESTS – DO NOT REMOVE

    public ResponseEntity<Category> createCategory(Category category) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoryService.createCategory(category));
    }

    public ResponseEntity<Void> deactivateCategory(long id) {
        categoryService.deactivateCategory(id);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(
                categoryService.getAllCategories()
        );
    }
}
