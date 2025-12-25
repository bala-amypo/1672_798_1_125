package com.example.demo.service.impl;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Category;
import com.example.demo.entity.MenuItem;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.MenuItemRepository;
import com.example.demo.repository.RecipeIngredientRepository;
import com.example.demo.service.MenuItemService;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final CategoryRepository categoryRepository;

    // 🔴 EXACT constructor order (DO NOT CHANGE)
    public MenuItemServiceImpl(
            MenuItemRepository menuItemRepository,
            RecipeIngredientRepository recipeIngredientRepository,
            CategoryRepository categoryRepository
    ) {
        this.menuItemRepository = menuItemRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public MenuItem createMenuItem(MenuItem item) {

        if (item.getSellingPrice() == null ||
                item.getSellingPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("price");
        }

        menuItemRepository.findByNameIgnoreCase(item.getName())
                .ifPresent(m -> {
                    throw new BadRequestException("Menu item already exists");
                });

        if (item.getCategories() != null && !item.getCategories().isEmpty()) {

            Set<Category> validatedCategories = new HashSet<>();

            for (Category c : item.getCategories()) {
                Category category = categoryRepository.findById(c.getId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Category not found"));

                if (Boolean.FALSE.equals(category.getActive())) {
                    throw new BadRequestException("Category is inactive");
                }

                validatedCategories.add(category);
            }

            item.setCategories(validatedCategories);
        }

        return menuItemRepository.save(item);
    }

    @Override
    public MenuItem updateMenuItem(Long id, MenuItem item) {

        MenuItem existing = getMenuItemById(id);

        if (item.getSellingPrice() != null &&
                item.getSellingPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("price");
        }

        if (Boolean.TRUE.equals(item.getActive()) &&
                !recipeIngredientRepository.existsByMenuItemId(id)) {
            throw new BadRequestException("recipe");
        }

        if (item.getCategories() != null && !item.getCategories().isEmpty()) {

            Set<Category> validatedCategories = new HashSet<>();

            for (Category c : item.getCategories()) {
                Category category = categoryRepository.findById(c.getId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Category not found"));

                if (Boolean.FALSE.equals(category.getActive())) {
                    throw new BadRequestException("Category is inactive");
                }

                validatedCategories.add(category);
            }

            existing.setCategories(validatedCategories);
        }

        existing.setName(item.getName());
        existing.setSellingPrice(item.getSellingPrice());
        existing.setActive(item.getActive());

        return menuItemRepository.save(existing);
    }

    @Override
    public MenuItem getMenuItemById(Long id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));
    }

    @Override
    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    @Override
    public void deactivateMenuItem(Long id) {
        MenuItem item = getMenuItemById(id);
        item.setActive(false);
        menuItemRepository.save(item);
    }
}
