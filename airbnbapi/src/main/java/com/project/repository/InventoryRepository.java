package com.project.repository;

import com.project.entity.Hotel;
import com.project.entity.Inventory;
import com.project.entity.Room;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    void deleteByRoom(Room room);

    @Query(""" 
            SELECT DISTINCT i.hotel FROM Inventory i
            WHERE  i.city = :city
                AND i.date BETWEEN :checkInDate AND :checkOutDate
                AND i.closed = false
                AND (i.totalCount -i.bookedCount -i.reservedCount) >= :roomsCount
            GROUP BY i.hotel , i.room
            HAVING COUNT(DISTINCT i.date) >= :dateCount
            """)
    Page<Hotel> findHotelWithAvailabileInventory(@Param("city") String city,
                                                 @Param("checkInDate") LocalDate checkInDate,
                                                 @Param("checkOutDate") LocalDate checkOutDate,
                                                 @Param("roomsCount") Integer roomsCount,
                                                 @Param("dateCount") Long dateCount,
                                                 Pageable pageable
                                                );


    @Query("""
            SELECT i from Inventory i
            WHERE i.room.id = :roomID
                AND i.date BETWEEN :checkInDate AND :checkOutDate
                AND i.closed = false
                AND (i.totalCount -i.bookedCount - i.reservedCount) >= :roomsCount
            """)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Inventory> findAndLockAvailableInventory(@Param("roomID") Long roomId,
                                                  @Param("checkInDate") LocalDate checkInDate,
                                                  @Param("checkOutDate") LocalDate checkOutDate,
                                                  @Param("roomsCount") Integer roomsCount,
                                                  @Param("dateCount") Long dateCount);


    List<Inventory> findByHotelAndDateBetween(Hotel hotel, LocalDate startDate, LocalDate endDate);
}
