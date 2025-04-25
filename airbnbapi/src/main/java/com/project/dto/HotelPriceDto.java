package com.project.dto;

import com.project.entity.Hotel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HotelPriceDto {

    private Hotel hotel;
    private double price;
}
