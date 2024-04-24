package com.library.repository;

import com.library.model.Book;
import com.library.model.Rental;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RentalRepo extends MongoRepository<Rental,String> {
    boolean existsByBookId(String bookId);
}
