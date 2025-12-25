package com.example.demo.controller;

import com.example.demo.entity.MenuItem;
import com.example.demo.service.MenuItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/menu-items")
@Tag(name = "Menu Items")
public class MenuItemController {

    private final MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    // ===================== REST ENDPOINTS =====================

    @PostMapping
    public ResponseEntity<MenuItem> create(@RequestBody MenuItem item) {
        return ResponseEntity.ok(
                menuItemService.createMenuItem(item)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuItem> update(
            @PathVariable Long id,
            @RequestBody MenuItem item
    ) {
        return ResponseEntity.ok(
                menuItemService.updateMenuItem(id, item)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> get(@PathVariable Long id) {
        return ResponseEntity.ok(
                menuItemService.getMenuItemById(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<MenuItem>> list() {
        return ResponseEntity.ok(
                menuItemService.getAllMenuItems()
        );
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        menuItemService.deactivateMenuItem(id);
        return ResponseEntity.ok().build();
    }

    // ===================== TEST-EXPECTED METHODS =====================
    // 🔥 REQUIRED FOR JUNIT TESTS – DO NOT REMOVE

    public ResponseEntity<MenuItem> createMenuItem(MenuItem item) {
        return ResponseEntity.ok(
                menuItemService.createMenuItem(item)
        );
    }

    public ResponseEntity<Void> deactivateMenuItem(long id) {
        menuItemService.deactivateMenuItem(id);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<List<MenuItem>> getAllMenuItems() {
        return ResponseEntity.ok(
                menuItemService.getAllMenuItems()
        );
    }
}
