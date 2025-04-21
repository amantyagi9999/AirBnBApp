package com.project.dto;

import com.project.entity.Hotel;
import com.project.entity.Room;
import com.project.entity.User;
import com.project.entity.enums.BookingStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class BookingDto {
    private Long id;


    private Integer roomsCount;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;
    private LocalDateTime updatedAt;

    private BookingStatus bookingStatus;

    private Set<GuestDto> guests;
}
