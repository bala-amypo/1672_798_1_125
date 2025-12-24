package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "categories", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    private Boolean active = true;

    @ManyToMany(mappedBy = "categories")
    private Set<MenuItem> menuItems = new HashSet<>();

    // ===================== GETTERS =====================

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getActive() {
        return active;
    }

    public Set<MenuItem> getMenuItems() {
        return menuItems;
    }

    // ===================== SETTERS =====================

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setMenuItems(Set<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    // ===================== SAFE HELPER METHODS =====================

    public void addMenuItem(MenuItem item) {
        this.menuItems.add(item);
        item.getCategories().add(this);
    }

    public void removeMenuItem(MenuItem item) {
        this.menuItems.remove(item);
        item.getCategories().remove(this);
    }
}
