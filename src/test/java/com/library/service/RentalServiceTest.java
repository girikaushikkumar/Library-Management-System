package com.library.service;

import com.library.exception.ResourceNotFoundException;
import com.library.model.Rental;
import com.library.payload.ApiResponse;
import com.library.repository.RentalRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RentalServiceTest {
    @Mock
    private RentalRepo rentalRepo;

    @InjectMocks
    private RentalService rentalService;

    private Rental rental;

    @BeforeEach
    void setUp() {
        rental = new Rental("1", "book1", "John Doe", new Date(), new Date());
    }

    @Test
    void RentBookAvailableTest() {
        when(rentalRepo.existsByBookId("book1")).thenReturn(true);

        ApiResponse response = rentalService.rentBook(rental);

        assertNotNull(response);
        assertEquals("Book is not available", response.getMessage());
        verify(rentalRepo, never()).save(any());
    }

    @Test
    void RentBookNotAvailableTest() {
        when(rentalRepo.existsByBookId("book1")).thenReturn(false);

        ApiResponse response = rentalService.rentBook(rental);

        assertNotNull(response);
        assertEquals("Book rented successfully", response.getMessage());
        verify(rentalRepo, times(1)).save(rental);
    }

    @Test
    void BookAvailableForRentBookAvailableTest() {
        when(rentalRepo.existsByBookId("book1")).thenReturn(true);

        boolean result = rentalService.BookAvailableForRent("book1");

        assertFalse(result);
    }

    @Test
    void BookAvailableForRent_BookNotAvailableTest() {
        when(rentalRepo.existsByBookId("book1")).thenReturn(false);

        boolean result = rentalService.BookAvailableForRent("book1");

        assertTrue(result);
    }

    @Test
    void GetAllRentalRecordsTest() {
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        when(rentalRepo.findAll()).thenReturn(rentals);

        List<Rental> result = rentalService.getAllRentalRecords();

        assertEquals(rentals, result);
    }

    @Test
    void CheckOverdueRentals_RentalUpToDateTest() {
        when(rentalRepo.findById("1")).thenReturn(Optional.of(rental));

        ApiResponse response = rentalService.checkOverdueRentals("1");

        assertNotNull(response);
        assertEquals("rental are up to date, with none exceeding the rental period limit", response.getMessage());
    }

    @Test
    void CheckOverdueRentals_RentalOverdueTest() {
        rental.setRentalDate(new Date(System.currentTimeMillis() - (15 * 24 * 60 * 60 * 1000))); // Set rental date 15 days ago

        when(rentalRepo.findById("1")).thenReturn(Optional.of(rental));

        ApiResponse response = rentalService.checkOverdueRentals("1");

        assertNotNull(response);
        assertEquals("Your book rental is overdue by 1 days", response.getMessage());
    }

    @Test
    void CheckOverdueRentals_RentalNotFoundTest() {
        when(rentalRepo.findById("1")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> rentalService.checkOverdueRentals("1"));
    }
}
