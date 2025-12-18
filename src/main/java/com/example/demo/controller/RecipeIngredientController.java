package com.example.demo.controller;

import com.example.demo.entity.RecipeIngredient;
import com.example.demo.service.RecipeIngredientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipe-ingredients")
@Tag(name = "Recipe Ingredients")
public class RecipeIngredientController {

    private final RecipeIngredientService recipeIngredientService;

    public RecipeIngredientController(RecipeIngredientService recipeIngredientService) {
        this.recipeIngredientService = recipeIngredientService;
    }

    @PostMapping
    public RecipeIngredient add(
            @RequestParam Long menuItemId,
            @RequestParam Long ingredientId,
            @RequestParam Double quantity
    ) {
        return recipeIngredientService.addIngredientToRecipe(menuItemId, ingredientId, quantity);
    }

    @PutMapping("/{id}")
    public RecipeIngredient update(@PathVariable Long id, @RequestParam Double quantity) {
        return recipeIngredientService.updateRecipeIngredient(id, quantity);
    }

    @GetMapping("/menu-item/{menuItemId}")
    public List<RecipeIngredient> list(@PathVariable Long menuItemId) {
        return recipeIngredientService.getIngredientsByMenuItem(menuItemId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        recipeIngredientService.removeIngredientFromRecipe(id);
    }

    @GetMapping("/ingredient/{ingredientId}/total-quantity")
    public Double totalQuantity(@PathVariable Long ingredientId) {
        return recipeIngredientService.getTotalQuantityOfIngredient(ingredientId);
    }
}
