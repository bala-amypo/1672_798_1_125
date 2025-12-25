package com.example.demo.controller;

import com.example.demo.entity.Ingredient;
import com.example.demo.service.IngredientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
@Tag(name = "Ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    // ===================== REST ENDPOINTS =====================

    @PostMapping
    public Ingredient create(@RequestBody Ingredient ingredient) {
        return ingredientService.createIngredient(ingredient);
    }

    @PutMapping("/{id}")
    public Ingredient update(@PathVariable Long id, @RequestBody Ingredient ingredient) {
        return ingredientService.updateIngredient(id, ingredient);
    }

    @GetMapping("/{id}")
    public Ingredient get(@PathVariable Long id) {
        return ingredientService.getIngredientById(id);
    }

    @GetMapping
    public List<Ingredient> list() {
        return ingredientService.getAllIngredients();
    }

    @PutMapping("/{id}/deactivate")
    public void deactivate(@PathVariable Long id) {
        ingredientService.deactivateIngredient(id);
    }

    // ===================== TEST-EXPECTED METHODS =====================
    // 🔥 DO NOT REMOVE – REQUIRED FOR JUNIT TESTS

    public Ingredient createIngredient(Ingredient ingredient) {
        return ingredientService.createIngredient(ingredient);
    }

    public void deactivateIngredient(long id) {
        ingredientService.deactivateIngredient(id);
    }

    public List<Ingredient> getAllIngredients() {
        return ingredientService.getAllIngredients();
    }
}
