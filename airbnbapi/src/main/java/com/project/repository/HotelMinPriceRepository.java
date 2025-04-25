package com.project.repository;

import com.project.dto.HotelPriceDto;
import com.project.entity.Hotel;
import com.project.entity.HotelMinPrice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface HotelMinPriceRepository extends JpaRepository<HotelMinPrice, Long> {


    @Query(""" 
            SELECT new com.project.dto.HotelPriceDto(i.hotel, AVG(i.price))
            FROM HotelMinPrice i
            WHERE  i.hotel.city = :city
                AND i.date BETWEEN :checkInDate AND :checkOutDate
                AND i.hotel.active = true
            GROUP BY i.hotel
            """)
    Page<HotelPriceDto> findHotelWithAvailabileInventory(@Param("city") String city,
                                                         @Param("checkInDate") LocalDate checkInDate,
                                                         @Param("checkOutDate") LocalDate checkOutDate,
                                                         @Param("roomsCount") Integer roomsCount,
                                                         @Param("dateCount") Long dateCount,
                                                         Pageable pageable
    );

    Optional<HotelMinPrice> findByHotelAndDate(Hotel hotel, LocalDate date);
}
