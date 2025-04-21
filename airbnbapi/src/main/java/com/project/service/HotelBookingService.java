package com.project.service;

import com.project.dto.BookingDto;
import com.project.dto.BookingRequest;
import com.project.dto.GuestDto;

import java.util.List;

public interface HotelBookingService {
    BookingDto createBooking(BookingRequest bookingRequest);

    BookingDto addGuestsToBooking(Long bookingId, List<GuestDto> guestDtoList);
}
