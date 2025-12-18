package com.example.demo.controller;

import com.example.demo.entity.ProfitCalculationRecord;
import com.example.demo.service.ProfitCalculationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profit")
@Tag(name = "Profit Calculation")
public class ProfitCalculationController {

    private final ProfitCalculationService profitCalculationService;

    public ProfitCalculationController(ProfitCalculationService profitCalculationService) {
        this.profitCalculationService = profitCalculationService;
    }

    @PostMapping("/calculate/{menuItemId}")
    public ProfitCalculationRecord calculate(@PathVariable Long menuItemId) {
        return profitCalculationService.calculateProfit(menuItemId);
    }

    @GetMapping("/{id}")
    public ProfitCalculationRecord get(@PathVariable Long id) {
        return profitCalculationService.getCalculationById(id);
    }

    @GetMapping("/menu-item/{menuItemId}")
    public List<ProfitCalculationRecord> history(@PathVariable Long menuItemId) {
        return profitCalculationService.getCalculationsForMenuItem(menuItemId);
    }

    @GetMapping
    public List<ProfitCalculationRecord> list() {
        return profitCalculationService.getAllCalculations();
    }
}
