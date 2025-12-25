package com.example.demo.controller;

import com.example.demo.entity.Ingredient;
import com.example.demo.service.IngredientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

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
    public ResponseEntity<Ingredient> create(@RequestBody Ingredient ingredient) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ingredientService.createIngredient(ingredient));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> update(
            @PathVariable Long id,
            @RequestBody Ingredient ingredient
    ) {
        return ResponseEntity.ok(
                ingredientService.updateIngredient(id, ingredient)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> get(@PathVariable Long id) {
        return ResponseEntity.ok(
                ingredientService.getIngredientById(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<Ingredient>> list() {
        return ResponseEntity.ok(
                ingredientService.getAllIngredients()
        );
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        ingredientService.deactivateIngredient(id);
        return ResponseEntity.ok().build();
    }

    // ===================== TEST-EXPECTED METHODS =====================
    // 🔥 REQUIRED FOR JUNIT TESTS – DO NOT REMOVE

    public ResponseEntity<Ingredient> createIngredient(Ingredient ingredient) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ingredientService.createIngredient(ingredient));
    }

    public ResponseEntity<Void> deactivateIngredient(long id) {
        ingredientService.deactivateIngredient(id);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        return ResponseEntity.ok(
                ingredientService.getAllIngredients()
        );
    }
}
