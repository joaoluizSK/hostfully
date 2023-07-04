package com.hostfully.bookings.controller;

import com.hostfully.bookings.entities.Booking;
import com.hostfully.bookings.exceptions.DatesOverlappingException;
import com.hostfully.bookings.exceptions.EndDateBeforeStartDateException;
import com.hostfully.bookings.exceptions.NotFoundException;
import com.hostfully.bookings.services.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookingController {

    final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<Booking>> getBookings() {
        return new ResponseEntity<>(bookingService.getAllBookings(), HttpStatus.OK);
    }

    @GetMapping("/bookings/{id}")
    public ResponseEntity<Booking> getBookingsById(@PathVariable("id") Long id) throws NotFoundException {
        try {
            return new ResponseEntity<>(bookingService.getBookingsById(id), HttpStatus.OK);
        } catch(NotFoundException exception) {
            throw new NotFoundException();
        }
    }

    @PostMapping(value = "/bookings")
    public ResponseEntity<Booking> create(@RequestBody Booking booking) throws DatesOverlappingException, EndDateBeforeStartDateException {
        try {
            return new ResponseEntity<>(bookingService.create(booking), HttpStatus.CREATED);
        } catch (DatesOverlappingException ex) {
            throw new DatesOverlappingException();
        } catch (EndDateBeforeStartDateException e) {
            throw new EndDateBeforeStartDateException();
        }
    }

    @PutMapping(value = "/bookings/{id}")
    public ResponseEntity<Booking> update(@PathVariable("id") Long id, @RequestBody Booking booking) throws NotFoundException, DatesOverlappingException, EndDateBeforeStartDateException {
        try {
            return new ResponseEntity<>(bookingService.update(id, booking), HttpStatus.OK);
        } catch(NotFoundException exception) {
            throw new NotFoundException();
        } catch (EndDateBeforeStartDateException e) {
            throw new EndDateBeforeStartDateException();
        }
    }

    @DeleteMapping(value = "/bookings/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id) {
        try {
            bookingService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
