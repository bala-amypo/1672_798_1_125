package com.example.demo.repository;

import com.example.demo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Existing method (keep it)
    Optional<Category> findByName(String name);

    // 🔥 REQUIRED FOR TESTS
    Optional<Category> findByNameIgnoreCase(String name);
}
