package com.project.strategy;

import com.project.entity.Inventory;

import java.math.BigDecimal;

public interface PricingStrategy {

    public BigDecimal calculatePrice(Inventory inventory);
}
