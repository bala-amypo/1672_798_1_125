package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "menu_items", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @Column(nullable = false)
    private BigDecimal sellingPrice;

    private Boolean active = true;

    private Timestamp createdAt;
    private Timestamp updatedAt;

    @ManyToMany
    @JoinTable(
        name = "menu_item_categories",
        joinColumns = @JoinColumn(name = "menu_item_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        createdAt = new Timestamp(System.currentTimeMillis());
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Timestamp(System.currentTimeMillis());
    }

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

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public Boolean getActive() {
        return active;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public Set<Category> getCategories() {
        return categories;
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

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    // ===================== SAFE HELPER METHODS =====================
    // (Needed for tests & bidirectional consistency)

    public void addCategory(Category category) {
        this.categories.add(category);
        category.getMenuItems().add(this);
    }

    public void removeCategory(Category category) {
        this.categories.remove(category);
        category.getMenuItems().remove(this);
    }
}
