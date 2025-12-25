package com.example.demo.repository;

import com.example.demo.entity.ProfitCalculationRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfitCalculationRecordRepository
        extends JpaRepository<ProfitCalculationRecord, Long> {

    // already required
    List<ProfitCalculationRecord> findByMenuItemId(Long menuItemId);

    // already added earlier
    List<ProfitCalculationRecord> findByProfitMarginBetween(double min, double max);

    // 🔥 REQUIRED BY JUNIT TESTS
    List<ProfitCalculationRecord> findByProfitMarginGreaterThanEqual(double margin);
}
