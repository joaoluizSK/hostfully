package com.hostfully.bookings.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hostfully.bookings.entities.Booking;
import com.hostfully.bookings.exceptions.EndDateBeforeStartDateException;
import com.hostfully.bookings.services.BookingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingController.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private BookingService bookingService;

    @Test
    void bookingsShouldReturnListOfBookings() throws Exception {
        Mockito.when(bookingService.getAllBookings()).thenReturn(new ArrayList<>());

        this.mockMvc.perform(get("/bookings")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void bookingsShouldCreateABooking() throws Exception {

        Booking testBooking = new Booking();
        testBooking.setPropertyId(1L);
        testBooking.setPersonId(1L);
        testBooking.setStartDate(Timestamp.valueOf("2023-01-01 00:00:00.000"));
        testBooking.setEndDate(Timestamp.valueOf("2023-02-01 00:00:00.000"));

        Mockito.when(bookingService.create(any(Booking.class))).thenReturn(testBooking);

        this.mockMvc.perform(post("/bookings")
                .content(mapper.writeValueAsString(testBooking))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.propertyId").value(testBooking.getPropertyId()));
    }

    @Test
    void bookingsShouldNotCreateABookingBecauseEndDateIsBeforeStartDate() throws Exception {

        Booking testBooking = new Booking();
        testBooking.setPropertyId(1L);
        testBooking.setPersonId(1L);
        testBooking.setStartDate(Timestamp.valueOf("2023-01-01 00:00:00.000"));
        testBooking.setEndDate(Timestamp.valueOf("2022-02-01 00:00:00.000"));

        Mockito.when(bookingService.create(any(Booking.class))).thenThrow(EndDateBeforeStartDateException.class);

        this.mockMvc.perform(post("/bookings")
                        .content(mapper.writeValueAsString(testBooking))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EndDateBeforeStartDateException))
                .andExpect(result -> assertEquals("End Date is before Start Date", result.getResolvedException().getMessage()));
    }

    @Test
    void bookingsShouldDeleteABooking() throws Exception {
        this.mockMvc.perform(delete("/bookings/1")).andDo(print()).andExpect(status().isNoContent());
    }
}
