package com.project.service;

import com.project.dto.BookingDto;
import com.project.dto.BookingRequest;
import com.project.dto.GuestDto;
import com.project.entity.*;
import com.project.entity.enums.BookingStatus;
import com.project.exception.ResourceNotFoundException;
import com.project.exception.UnAuthorizedAccessException;
import com.project.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelBookingServiceImpl implements  HotelBookingService {

    private final HotelBookingRepository hotelBookingRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final InventoryRepository inventoryRepository;
    private final GuestRepository guestRepository;
    private final ModelMapper modelMapper;


    @Override
    @Transactional
    public BookingDto createBooking(BookingRequest bookingRequest) {
        Hotel hotel =hotelRepository.findById(bookingRequest.getHotelId())
                    .orElseThrow(()-> new ResourceNotFoundException("Hotel not found with id {}"+bookingRequest.getHotelId()));
        Room room = roomRepository.findById(bookingRequest.getRoomId())
                    .orElseThrow(()-> new ResourceNotFoundException("Room not found with id {}"+bookingRequest.getRoomId()));
        Long daysCount = ChronoUnit.DAYS.between(bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate())+1;
        List<Inventory> inventoryList = inventoryRepository.findAndLockAvailableInventory(bookingRequest.getRoomId(), bookingRequest.getCheckInDate(),
                    bookingRequest.getCheckOutDate(), bookingRequest.getRoomsCount(), daysCount);
        if(inventoryList.size() < daysCount)
            throw new IllegalStateException("Rooms not availabe anymore");

        for(Inventory i : inventoryList){
            i.setReservedCount(i.getReservedCount()+bookingRequest.getRoomsCount());
        }
        inventoryRepository.saveAll(inventoryList);


        User user = new User();
        user.setId(1L);  // ToDo Dummy user

        // TODO calculate dynamic pricing amount

        Booking booking = Booking.builder()
                .bookingStatus(BookingStatus.RESERVED)
                .hotel(hotel)
                .room(room)
                .checkInDate(bookingRequest.getCheckInDate())
                .checkOutDate(bookingRequest.getCheckOutDate())
                .roomsCount(bookingRequest.getRoomsCount())
                .user(getCurrentUser())
                .amount(BigDecimal.TEN)
                .build();

        booking = hotelBookingRepository.save(booking);
        return modelMapper.map(booking, BookingDto.class);
    }

    @Override
    @Transactional
    public BookingDto addGuestsToBooking(Long bookingId, List<GuestDto> guestDtoList) {

        Booking booking = hotelBookingRepository.findById(bookingId)
                .orElseThrow(()-> new ResourceNotFoundException("Booking not found with id {}"+bookingId));

        User user = getCurrentUser();
        if(!user.equals(booking.getUser()))
            throw new UnAuthorizedAccessException("You are not allowed to add guest to this booking"+ user.getId() + user.getEmail());

        if(hasBookingExpired(booking))
            throw new IllegalStateException("Booking has been expired");

        if(booking.getBookingStatus() != BookingStatus.RESERVED)
            throw new IllegalStateException("Booking is not in RESERVED state, guest can not be added");

        List<Guest> guestList = new ArrayList<>();
        for(GuestDto guestDto : guestDtoList){
            Guest guest = modelMapper.map(guestDto, Guest.class);
            guest.setUser(getCurrentUser());
            guest = guestRepository.save(guest);
            booking.getGuests().add(guest);
        }

        booking.setBookingStatus(BookingStatus.GUESTS_ADDED);
        booking = hotelBookingRepository.save(booking);
        return modelMapper.map(booking, BookingDto.class);

    }

    public boolean hasBookingExpired(Booking booking){
        return booking.getCreatedAt().plusMinutes(10).isBefore(LocalDateTime.now());
    }

    public User getCurrentUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
