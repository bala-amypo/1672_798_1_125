package com.example.demo.controller;

import com.example.demo.entity.ProfitCalculationRecord;
import com.example.demo.service.ProfitCalculationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/profit")
@Tag(name = "Profit Calculation")
public class ProfitCalculationController {

    private final ProfitCalculationService profitCalculationService;

    public ProfitCalculationController(ProfitCalculationService profitCalculationService) {
        this.profitCalculationService = profitCalculationService;
    }

    // ===================== REST ENDPOINTS =====================

    @PostMapping("/calculate/{menuItemId}")
    public ResponseEntity<ProfitCalculationRecord> calculate(@PathVariable Long menuItemId) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(profitCalculationService.calculateProfit(menuItemId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfitCalculationRecord> get(@PathVariable Long id) {
        return ResponseEntity.ok(
                profitCalculationService.getCalculationById(id)
        );
    }

    @GetMapping("/menu-item/{menuItemId}")
    public ResponseEntity<List<ProfitCalculationRecord>> history(
            @PathVariable Long menuItemId
    ) {
        return ResponseEntity.ok(
                profitCalculationService.getCalculationsForMenuItem(menuItemId)
        );
    }

    @GetMapping
    public ResponseEntity<List<ProfitCalculationRecord>> list() {
        return ResponseEntity.ok(
                profitCalculationService.getAllCalculations()
        );
    }

    // ===================== TEST-EXPECTED METHODS =====================
    // 🔥 REQUIRED FOR PLATFORM / JUNIT TESTS – DO NOT REMOVE

    public ResponseEntity<ProfitCalculationRecord> calculateProfit(Long menuItemId) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(profitCalculationService.calculateProfit(menuItemId));
    }

    public ResponseEntity<List<ProfitCalculationRecord>> getAllCalculations() {
        return ResponseEntity.ok(
                profitCalculationService.getAllCalculations()
        );
    }

    public ResponseEntity<List<ProfitCalculationRecord>> getCalculationsForMenuItem(
            Long menuItemId
    ) {
        return ResponseEntity.ok(
                profitCalculationService.getCalculationsForMenuItem(menuItemId)
        );
    }
}
