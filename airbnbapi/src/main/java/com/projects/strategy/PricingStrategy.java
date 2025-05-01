package com.projects.strategy;

import com.projects.entity.Inventory;

import java.math.BigDecimal;
public interface PricingStrategy {

    BigDecimal calculatePrice(Inventory inventory);
}
