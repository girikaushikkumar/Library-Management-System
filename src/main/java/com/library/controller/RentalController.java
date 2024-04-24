package com.library.controller;

import com.library.model.Rental;
import com.library.payload.ApiResponse;
import com.library.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/rental/")
public class RentalController {
    @Autowired
    private RentalService rentalService;

    @PostMapping("rentBook")
    public ResponseEntity<ApiResponse> rentBook(@RequestBody Rental rental) {
        ApiResponse apiResponse = this.rentalService.rentBook(rental);
        return new ResponseEntity<>(apiResponse,HttpStatus.CREATED);
    }

    @GetMapping("bookAvailableForRent/{bookId}")
    public ResponseEntity<ApiResponse> BookAvailableForRent(@PathVariable String bookId) {
        boolean book = this.rentalService.BookAvailableForRent(bookId);
        ApiResponse apiResponse = new ApiResponse();
        if(book) {
            apiResponse.setMessage("book is not available");
        } else {
            apiResponse.setMessage("book is avilable");
        }
        return new ResponseEntity<>(apiResponse, HttpStatus.FOUND);
    }

    @GetMapping("getAllRentalRecords")
    public ResponseEntity<List<Rental>> getAllRentalRecords() {
        List<Rental> rentals = this.rentalService.getAllRentalRecords();
        return new ResponseEntity<>(rentals,HttpStatus.FOUND);
    }

    @GetMapping("checkOverdueRentals/{rentalId}")
    public ResponseEntity<ApiResponse> checkOverdueRentals(@PathVariable String rentalId) {
        ApiResponse apiResponse = this.rentalService.checkOverdueRentals(rentalId);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
}
