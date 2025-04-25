package com.project.service;

import com.project.dto.HotelDto;
import com.project.dto.HotelPriceDto;
import com.project.dto.HotelSearchDto;
import com.project.entity.Room;
import org.springframework.data.domain.Page;

public interface InventoryService {

    void initializeRoomForAYear(Room room);

    void deleteAllInventories(Room room);

    Page<HotelPriceDto> searchHotel(HotelSearchDto hotelSearchDto);
}
