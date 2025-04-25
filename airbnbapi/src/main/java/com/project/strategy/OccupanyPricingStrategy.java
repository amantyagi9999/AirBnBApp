package com.project.strategy;

import com.project.entity.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class OccupanyPricingStrategy implements  PricingStrategy{

    private final PricingStrategy wraped;
    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price = wraped.calculatePrice(inventory);
        double occupancyRate  = (double) inventory.getBookedCount() / inventory.getTotalCount();
        if(occupancyRate >0.8){
            price = price.multiply(BigDecimal.valueOf(1.2));
        }
        return price;
    }
}
