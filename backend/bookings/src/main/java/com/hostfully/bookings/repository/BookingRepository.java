package com.hostfully.bookings.repository;

import com.hostfully.bookings.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query(value = "SELECT b " +
                   "  FROM Booking b" +
                   " WHERE :bookingDate BETWEEN b.startDate AND b.endDate" +
                   "   AND b.propertyId = :propertyId")
    List<Booking> findBookingsByBookingDateAndPropertyId(@Param("bookingDate") Timestamp bookingDate, @Param("propertyId") Long propertyId);

    @Query(value = "SELECT b " +
            "  FROM Booking b" +
            " WHERE :bookingDate BETWEEN b.startDate AND b.endDate" +
            "   AND b.propertyId = :propertyId" +
            "   AND b.id != :bookingId")
    List<Booking> findBookingsByBookingDateAndPropertyIdAndDifferentBookingId(@Param("bookingDate") Timestamp bookingDate, @Param("propertyId") Long propertyId, @Param("bookingId") Long bookingId);


}
