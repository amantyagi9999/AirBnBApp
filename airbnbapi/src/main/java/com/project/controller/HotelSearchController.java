package com.project.controller;

import com.project.dto.HotelDto;
import com.project.dto.HotelInfoDto;
import com.project.dto.HotelPriceDto;
import com.project.dto.HotelSearchDto;
import com.project.service.HotelService;
import com.project.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/hotels")
public class HotelSearchController {

    private final InventoryService inventoryService;
    private final HotelService hotelService;

    @PostMapping("/search")
    public ResponseEntity<Page<HotelPriceDto>> searchHotel(@RequestBody HotelSearchDto hotelSearchDto){
        log.info("Search hotel with "+ hotelSearchDto.getCity() + " " + hotelSearchDto.getCheckInDate() + " " + hotelSearchDto.getCheckOutDate() + " " + hotelSearchDto.getRoomsCount());
        Page<HotelPriceDto> page = inventoryService.searchHotel(hotelSearchDto);
        return ResponseEntity.ok(page);
    }

}
