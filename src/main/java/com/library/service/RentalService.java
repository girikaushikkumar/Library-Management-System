package com.library.service;

import com.library.exception.ResourceNotFoundException;
import com.library.model.Rental;
import com.library.payload.ApiResponse;
import com.library.repository.RentalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalService{
    @Autowired
    private RentalRepo rentalRepo;

    public ApiResponse rentBook(Rental rental){
        boolean checkBook = this.rentalRepo.existsByBookId(rental.getBookId());
        if(checkBook) {
            return new ApiResponse("Book is not available");
        }
        this.rentalRepo.save(rental);
        return new ApiResponse("Book rented successfully");
    };

    public boolean BookAvailableForRent(String bookId) {
        boolean checkBook = this.rentalRepo.existsByBookId(bookId);
        return checkBook;
    }

    public List<Rental> getAllRentalRecords() {
        List<Rental> rentals = this.rentalRepo.findAll();
        return rentals;
    }

    public ApiResponse checkOverdueRentals(String rentalId) {
        Rental rental = this.rentalRepo.findById(rentalId).orElseThrow(() -> new ResourceNotFoundException("Rental","Id",rentalId));
        long rentalTime = rental.getRentalDate().getTime();
        long returnTime = rental.getReturnDate().getTime();
        long diff = returnTime-rentalTime;
        long days = diff/(1000*60*60*24);
        if(days <= 14)
            return new ApiResponse("rental are up to date, with none exceeding the rental period limit");
        return new ApiResponse("Your book rental is overdue by "+days+" days");
    }
}
