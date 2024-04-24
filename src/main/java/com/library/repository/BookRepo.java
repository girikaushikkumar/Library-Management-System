package com.library.repository;

import com.library.model.Book;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;


public interface BookRepo extends MongoRepository<Book,String> {
     boolean existsByIsbn(String isbn);
     Optional<Book> findByIsbn(String isbn);
     List<Book> findByAuthorId(String authorId);

}
