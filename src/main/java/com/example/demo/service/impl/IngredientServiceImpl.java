package com.example.demo.service.impl;

import com.example.demo.entity.MenuItem;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.MenuItemRepository;
import com.example.demo.repository.RecipeIngredientRepository;
import com.example.demo.service.MenuItemService;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final CategoryRepository categoryRepository;

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
            throw new BadRequestException("Invalid price");
        }

        menuItemRepository.findByNameIgnoreCase(item.getName())
                .ifPresent(m -> {
                    throw new BadRequestException("Duplicate menu item");
                });

        return menuItemRepository.save(item);
    }

    @Override
    public MenuItem updateMenuItem(Long id, MenuItem item) {

        MenuItem existing = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));

        if (item.getActive() != null && item.getActive()) {
            if (!recipeIngredientRepository.existsByMenuItemId(id)) {
                throw new BadRequestException(
                        "Menu item cannot be active without recipe ingredients"
                );
            }
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
