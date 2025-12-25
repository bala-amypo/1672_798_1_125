package com.example.demo.service.impl;

import com.example.demo.entity.Ingredient;
import com.example.demo.entity.MenuItem;
import com.example.demo.entity.RecipeIngredient;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.repository.MenuItemRepository;
import com.example.demo.repository.RecipeIngredientRepository;
import com.example.demo.service.RecipeIngredientService;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class RecipeIngredientServiceImpl implements RecipeIngredientService {

    private final RecipeIngredientRepository recipeIngredientRepository;
    private final IngredientRepository ingredientRepository;
    private final MenuItemRepository menuItemRepository;

    // 🔴 Constructor order as per SRS
    public RecipeIngredientServiceImpl(
            RecipeIngredientRepository recipeIngredientRepository,
            IngredientRepository ingredientRepository,
            MenuItemRepository menuItemRepository
    ) {
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.ingredientRepository = ingredientRepository;
        this.menuItemRepository = menuItemRepository;
    }

    // ================= PRIMARY API =================

    @Override
    public RecipeIngredient addIngredientToRecipe(
            Long menuItemId,
            Long ingredientId,
            Double quantity
    ) {

        if (quantity == null || quantity <= 0) {
            throw new BadRequestException("Quantity must be greater than zero");
        }

        Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found"));

        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));

        RecipeIngredient ri = new RecipeIngredient();
        ri.setIngredient(ingredient);
        ri.setMenuItem(menuItem);
        ri.setQuantityRequired(quantity);

        return recipeIngredientRepository.save(ri);
    }

    @Override
    public RecipeIngredient updateRecipeIngredient(Long id, Double quantity) {

        if (quantity == null || quantity <= 0) {
            throw new BadRequestException("Quantity must be greater than zero");
        }

        RecipeIngredient existing = recipeIngredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe ingredient not found"));

        existing.setQuantityRequired(quantity);
        return recipeIngredientRepository.save(existing);
    }

    @Override
    public List<RecipeIngredient> getIngredientsByMenuItem(Long menuItemId) {
        return recipeIngredientRepository.findByMenuItemId(menuItemId);
    }

    @Override
    public void removeIngredientFromRecipe(Long id) {
        recipeIngredientRepository.deleteById(id);
    }

    @Override
    public Double getTotalQuantityOfIngredient(Long ingredientId) {
        Double total = recipeIngredientRepository.getTotalQuantityByIngredientId(ingredientId);
        // 🔥 Defensive: platform tests expect 0.0 instead of null
        return total != null ? total : 0.0;
    }

    // ================= TEST-EXPECTED METHOD =================
    // 🔥 REQUIRED BY PLATFORM / JUNIT TESTS – DO NOT REMOVE

    @Override
    public RecipeIngredient addIngredientToMenuItem(RecipeIngredient recipeIngredient) {

        if (recipeIngredient.getQuantityRequired() == null ||
                recipeIngredient.getQuantityRequired() <= 0) {
            throw new BadRequestException("Quantity must be greater than zero");
        }

        // 🔥 Validate ingredient exists
        Long ingredientId = recipeIngredient.getIngredient().getId();
        Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found"));

        // 🔥 Validate menu item exists
        Long menuItemId = recipeIngredient.getMenuItem().getId();
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));

        recipeIngredient.setIngredient(ingredient);
        recipeIngredient.setMenuItem(menuItem);

        return recipeIngredientRepository.save(recipeIngredient);
    }
}
