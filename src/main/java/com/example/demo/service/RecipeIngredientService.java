package com.example.demo.service;

import com.example.demo.entity.RecipeIngredient;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface RecipeIngredientService {

    // Existing (keep)
    RecipeIngredient addIngredientToRecipe(
            Long menuItemId,
            Long ingredientId,
            Double quantity
    );

    // 🔥 REQUIRED BY TESTS
    RecipeIngredient addIngredientToMenuItem(RecipeIngredient recipeIngredient);

    RecipeIngredient updateRecipeIngredient(Long id, Double quantity);

    List<RecipeIngredient> getIngredientsByMenuItem(Long menuItemId);

    void removeIngredientFromRecipe(Long id);

    Double getTotalQuantityOfIngredient(Long ingredientId);
}
