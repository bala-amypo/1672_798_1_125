package com.example.demo.controller;

import com.example.demo.entity.RecipeIngredient;
import com.example.demo.service.RecipeIngredientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/recipe-ingredients")
@Tag(name = "Recipe Ingredients")
public class RecipeIngredientController {

    private final RecipeIngredientService recipeIngredientService;

    public RecipeIngredientController(RecipeIngredientService recipeIngredientService) {
        this.recipeIngredientService = recipeIngredientService;
    }

    // ===================== REST ENDPOINTS =====================

    @PostMapping
    public ResponseEntity<RecipeIngredient> add(
            @RequestParam Long menuItemId,
            @RequestParam Long ingredientId,
            @RequestParam Double quantity
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(recipeIngredientService.addIngredientToRecipe(menuItemId, ingredientId, quantity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeIngredient> update(
            @PathVariable Long id,
            @RequestParam Double quantity
    ) {
        return ResponseEntity.ok(
                recipeIngredientService.updateRecipeIngredient(id, quantity)
        );
    }

    @GetMapping("/menu-item/{menuItemId}")
    public ResponseEntity<List<RecipeIngredient>> list(@PathVariable Long menuItemId) {
        return ResponseEntity.ok(
                recipeIngredientService.getIngredientsByMenuItem(menuItemId)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        recipeIngredientService.removeIngredientFromRecipe(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/ingredient/{ingredientId}/total-quantity")
    public ResponseEntity<Double> totalQuantity(@PathVariable Long ingredientId) {
        return ResponseEntity.ok(
                recipeIngredientService.getTotalQuantityOfIngredient(ingredientId)
        );
    }

    // ===================== TEST-EXPECTED METHODS =====================
    // 🔥 REQUIRED FOR PLATFORM / JUNIT TESTS – DO NOT REMOVE

    public ResponseEntity<RecipeIngredient> addIngredientToMenuItem(
            Long menuItemId,
            Long ingredientId,
            Double quantity
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(recipeIngredientService.addIngredientToRecipe(menuItemId, ingredientId, quantity));
    }

    public ResponseEntity<List<RecipeIngredient>> getIngredientsByMenuItem(Long menuItemId) {
        return ResponseEntity.ok(
                recipeIngredientService.getIngredientsByMenuItem(menuItemId)
        );
    }

    public ResponseEntity<Double> getTotalQuantityOfIngredient(Long ingredientId) {
        return ResponseEntity.ok(
                recipeIngredientService.getTotalQuantityOfIngredient(ingredientId)
        );
    }
}
