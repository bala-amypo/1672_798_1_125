package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "recipe_ingredients")
public class RecipeIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private MenuItem menuItem;

    @ManyToOne(optional = false)
    private Ingredient ingredient;

    @Column(nullable = false)
    private Double quantityRequired;

    // ===================== GETTERS =====================

    public Long getId() {
        return id;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public Double getQuantityRequired() {
        return quantityRequired;
    }

    // 👉 TEST EXPECTED METHOD
    public Double getQuantity() {
        return quantityRequired;
    }

    // ===================== SETTERS =====================

    public void setId(Long id) {
        this.id = id;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public void setQuantityRequired(Double quantityRequired) {
        this.quantityRequired = quantityRequired;
    }

    // 👉 TEST EXPECTED METHOD
    public void setQuantity(Double quantity) {
        this.quantityRequired = quantity;
    }
}
