package com.library.repository;

import com.library.model.Author;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuthorRepo extends MongoRepository<Author,String> {
}
