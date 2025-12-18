package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "profit_calculation_records")
public class ProfitCalculationRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private MenuItem menuItem;

    private BigDecimal totalCost;
    private BigDecimal profitMargin;

    private Timestamp calculatedAt;

    @PrePersist
    protected void onCreate() {
        calculatedAt = new Timestamp(System.currentTimeMillis());
    }

    // Getters and Setters
    public Long getId() { return id; }
    public MenuItem getMenuItem() { return menuItem; }
    public BigDecimal getTotalCost() { return totalCost; }
    public BigDecimal getProfitMargin() { return profitMargin; }

    public void setId(Long id) { this.id = id; }
    public void setMenuItem(MenuItem menuItem) { this.menuItem = menuItem; }
    public void setTotalCost(BigDecimal totalCost) { this.totalCost = totalCost; }
    public void setProfitMargin(BigDecimal profitMargin) { this.profitMargin = profitMargin; }
}
