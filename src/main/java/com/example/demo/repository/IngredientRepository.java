package com.example.demo.repository;

import com.example.demo.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    // 🔴 THIS METHOD IS REQUIRED BY TESTS
    Optional<Ingredient> findByNameIgnoreCase(String name);
}
