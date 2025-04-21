package com.project.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class HotelSearchDto {

    private String city;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer roomsCount;

    private Integer page=0;
    private Integer size=10;
}
