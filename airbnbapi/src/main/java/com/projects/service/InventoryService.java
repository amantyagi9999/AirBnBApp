package com.projects.service;

import com.projects.dto.HotelPriceResponseDto;
import com.projects.dto.HotelSearchRequest;
import com.projects.dto.InventoryDto;
import com.projects.dto.UpdateInventoryRequestDto;
import com.projects.entity.Room;
import org.springframework.data.domain.Page;

import java.util.List;

public interface InventoryService {

    void initializeRoomForAYear(Room room);

    void deleteAllInventories(Room room);

    Page<HotelPriceResponseDto> searchHotels(HotelSearchRequest hotelSearchRequest);

    List<InventoryDto> getAllInventoryByRoom(Long roomId);

    void updateInventory(Long roomId, UpdateInventoryRequestDto updateInventoryRequestDto);
}
