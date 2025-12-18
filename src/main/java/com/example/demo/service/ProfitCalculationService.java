package com.example.demo.service.impl;

import com.example.demo.entity.ProfitCalculationRecord;
import java.util.List;
import org.springframework.stereotype.Service;


@Service
public interface ProfitCalculationService {
    ProfitCalculationRecord calculateProfit(Long menuItemId);
    ProfitCalculationRecord getCalculationById(Long id);
    List<ProfitCalculationRecord> getCalculationsForMenuItem(Long menuItemId);
    List<ProfitCalculationRecord> getAllCalculations();
}
