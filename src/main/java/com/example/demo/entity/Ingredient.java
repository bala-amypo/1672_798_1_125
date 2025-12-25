package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // ✅ cost per unit
    @Column(nullable = false)
    private BigDecimal unitCost;

    // ✅ REQUIRED BY TESTS
    private String unit;

    private Boolean active = true;

    // ================= GETTERS =================

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    // 🔥 FIX FOR ERROR #2
    public String getUnit() {
        return unit;
    }

    public Boolean getActive() {
        return active;
    }

    // ================= SETTERS =================

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    // 🔥 FIX FOR ERROR #2
    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
