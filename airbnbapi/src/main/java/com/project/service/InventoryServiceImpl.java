package com.project.service;

import com.project.dto.HotelDto;
import com.project.dto.HotelPriceDto;
import com.project.dto.HotelSearchDto;
import com.project.entity.Hotel;
import com.project.entity.Inventory;
import com.project.entity.Room;
import com.project.repository.HotelMinPriceRepository;
import com.project.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService{

    private final InventoryRepository inventoryRepository;
    private final HotelMinPriceRepository hotelMinPriceRepository;
    private final ModelMapper modelMapper;

    @Override
    public void initializeRoomForAYear(Room room) {
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusYears(1);
        for (; !today.isAfter(endDate); today=today.plusDays(1)) {
            Inventory inventory = Inventory.builder()
                    .hotel(room.getHotel())
                    .room(room)
                    .bookedCount(0)
                    .city(room.getHotel().getCity())
                    .date(today)
                    .price(room.getBasePrice())
                    .surgeFactor(BigDecimal.ONE)
                    .totalCount(room.getTotalCount())
                    .closed(false)
                    .build();
            inventoryRepository.save(inventory);
        }
    }

    @Override
    public void deleteAllInventories(Room room) {
        LocalDate today = LocalDate.now();
        inventoryRepository.deleteByRoom(room);
    }

    @Override
    public Page<HotelPriceDto> searchHotel(HotelSearchDto hotelSearchDto) {
        Pageable pageable = PageRequest.of(hotelSearchDto.getPage(), hotelSearchDto.getSize());
        long dateCount = ChronoUnit.DAYS.between(hotelSearchDto.getCheckInDate(), hotelSearchDto.getCheckOutDate())+1;
        Page<HotelPriceDto> pageHotel = hotelMinPriceRepository.findHotelWithAvailabileInventory(hotelSearchDto.getCity(), hotelSearchDto.getCheckInDate(),
                                                                hotelSearchDto.getCheckOutDate(), hotelSearchDto.getRoomsCount(),dateCount, pageable);

        return pageHotel.map((element) -> modelMapper.map(element, HotelPriceDto.class));
    }
}
