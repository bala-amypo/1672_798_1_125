package com.example.demo.service;

import com.example.demo.entity.MenuItem;
import java.util.List;
@Service
public interface MenuItemService {
    MenuItem createMenuItem(MenuItem item);
    MenuItem updateMenuItem(Long id, MenuItem item);
    MenuItem getMenuItemById(Long id);
    List<MenuItem> getAllMenuItems();
    void deactivateMenuItem(Long id);
}
