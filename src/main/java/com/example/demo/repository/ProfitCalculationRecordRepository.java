package com.example.demo.repository;

import com.example.demo.entity.ProfitCalculationRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfitCalculationRecordRepository
        extends JpaRepository<ProfitCalculationRecord, Long> {

    // Existing method – REQUIRED by tests / service
    List<ProfitCalculationRecord> findByMenuItemId(Long menuItemId);

    // 🔴 REQUIRED to fix your compilation error
    List<ProfitCalculationRecord> findByProfitMarginBetween(double min, double max);
}
