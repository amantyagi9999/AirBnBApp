package com.project.controller;

import com.project.dto.BookingDto;
import com.project.dto.BookingRequest;
import com.project.dto.GuestDto;
import com.project.service.HotelBookingService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/bookings")
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class HotelBookingController {

    private final HotelBookingService hotelBookingService;

    @PostMapping("/create")
    public ResponseEntity<BookingDto> createBooking(@RequestBody BookingRequest bookingRequest ) {
       BookingDto bookingDto =  hotelBookingService.createBooking(bookingRequest);
        return new ResponseEntity<>(bookingDto, HttpStatus.CREATED);
    }

    @PostMapping("/{bookingId}/addGuests")
    public ResponseEntity<BookingDto> addGuestsToBooking(@PathVariable Long bookingId, @RequestBody List<GuestDto> guestDtoList){
        BookingDto bookingDto = hotelBookingService.addGuestsToBooking(bookingId, guestDtoList);
        return new ResponseEntity<BookingDto>(bookingDto, HttpStatus.CREATED);
    }

}
