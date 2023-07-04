package com.hostfully.bookings.services;

import com.hostfully.bookings.entities.Booking;
import com.hostfully.bookings.entities.Property;
import com.hostfully.bookings.exceptions.DatesOverlappingException;
import com.hostfully.bookings.exceptions.EndDateBeforeStartDateException;
import com.hostfully.bookings.exceptions.NotFoundException;
import com.hostfully.bookings.repository.BookingRepository;
import com.hostfully.bookings.repository.PropertyRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    private final PropertyRepository propertyRepository;

    public BookingService(BookingRepository bookingRepository, PropertyRepository propertyRepository) {
        this.bookingRepository = bookingRepository;
        this.propertyRepository = propertyRepository;
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingsById(Long id) throws NotFoundException {
        Optional<Booking> bookingData = bookingRepository.findById(id);
        if(bookingData.isPresent()) {
            return bookingData.get();
        }
        throw new NotFoundException();
    }

    public Booking create(Booking booking) throws DatesOverlappingException, EndDateBeforeStartDateException {
        if(isEndDateBeforeStartDate(booking)) {
            throw new EndDateBeforeStartDateException();
        }
        if(isValidBooking(booking)) {
            return bookingRepository.save(booking);
        }
        throw new DatesOverlappingException();
    }

    private boolean isValidBooking(Booking booking) {
        if(isOwner(booking)) {
            return true;
        }
        if(!isDatesOverlapping(booking)) {
            return true;
        }
        return false;
    }

    public Booking update(Long id, Booking booking) throws NotFoundException, DatesOverlappingException, EndDateBeforeStartDateException {
        if(isEndDateBeforeStartDate(booking)) {
            throw new EndDateBeforeStartDateException();
        }
        Optional<Booking> bookingData = bookingRepository.findById(id);
        if(bookingData.isPresent()) {
            Booking _booking = bookingData.get();
            _booking.setStartDate(booking.getStartDate());
            _booking.setEndDate(booking.getEndDate());
            _booking.setPersonId(booking.getPersonId());
            _booking.setPropertyId(booking.getPropertyId());
            if(isValidBooking(_booking)) {
                return bookingRepository.save(_booking);
            } else {
                throw new DatesOverlappingException();
            }
        }
        throw new NotFoundException();
    }

    public void deleteById(Long id) {
        bookingRepository.deleteById(id);
    }

    private boolean isOwner(Booking booking) {
        Optional<Property> property = propertyRepository.findById(booking.getPropertyId());
        if(property.isPresent()) {
            Property _property = property.get();
            return Objects.equals(booking.getPersonId(), _property.getOwnerId());
        }
        return false;
    }

    private boolean isDatesOverlapping(Booking booking) {
        List<Booking> bookings = new ArrayList<>();
        if(booking.getId() != null) {
            bookings.addAll(bookingRepository.findBookingsByBookingDateAndPropertyIdAndDifferentBookingId(booking.getStartDate(), booking.getPropertyId(), booking.getId()));
            bookings.addAll(bookingRepository.findBookingsByBookingDateAndPropertyIdAndDifferentBookingId(booking.getEndDate(), booking.getPropertyId(), booking.getId()));
        } else {
            bookings.addAll(bookingRepository.findBookingsByBookingDateAndPropertyId(booking.getStartDate(), booking.getPropertyId()));
            bookings.addAll(bookingRepository.findBookingsByBookingDateAndPropertyId(booking.getEndDate(), booking.getPropertyId()));
        }
        return !bookings.isEmpty();
    }

    private boolean isEndDateBeforeStartDate(Booking booking) {
        return booking.getEndDate().before(booking.getStartDate());
    }

}
