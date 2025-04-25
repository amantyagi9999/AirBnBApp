package com.project.strategy;

import com.project.entity.Inventory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PricingService {

    public BigDecimal calculateDynamicPrice(Inventory inventory){
        PricingStrategy basePrice = new BasePricingStrategy();

        basePrice = new SurgePricingStrategy(basePrice);
        basePrice = new UrgencyPricingStrategy(basePrice);
        basePrice = new HolidayPricingStrategy(basePrice);
        basePrice = new OccupanyPricingStrategy(basePrice);
        return basePrice.calculatePrice(inventory);
    }
}
