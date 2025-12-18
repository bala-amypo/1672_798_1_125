package com.example.demo.service.impl;

import com.example.demo.entity.MenuItem;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface MenuItemService {
    MenuItem createMenuItem(MenuItem item);
    MenuItem updateMenuItem(Long id, MenuItem item);
    MenuItem getMenuItemById(Long id);
    List<MenuItem> getAllMenuItems();
    void deactivateMenuItem(Long id);
}
