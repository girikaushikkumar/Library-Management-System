package com.library.service;

import com.library.dto.BookDto;
import com.library.dto.BookResponse;
import com.library.exception.ResourceNotFoundException;
import com.library.model.Author;
import com.library.model.Book;
import com.library.payload.ApiResponse;
import com.library.repository.AuthorRepo;
import com.library.repository.BookRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private AuthorRepo authorRepo;

    @Autowired
    private ModelMapper modelMapper;
    public ApiResponse addBook(BookDto bookDto,String authorId) {
        if(this.bookRepo.existsByIsbn(bookDto.getIsbn())) {
            return new ApiResponse("book already exit");
        }
        Book book = this.modelMapper.map(bookDto,Book.class);
        Author author = this.authorRepo.findById(authorId).orElseThrow(()->new ResourceNotFoundException("author","id",authorId));
        book.setAuthor(author);
        this.bookRepo.save(book);
        return new ApiResponse("book added successfully");
    }

    public void UpdateBook(BookDto bookDto,String isbn) {
        Book book = this.bookRepo.findByIsbn(isbn).orElseThrow(()-> new ResourceNotFoundException("book","isbn",isbn));
        book.setTitle(bookDto.getTitle());
        book.setPublicationYear(bookDto.getPublicationYear());
        this.bookRepo.save(book);
    }

    public BookResponse getBookById(String id) {
        Book book = this.bookRepo.findByIsbn(id).orElseThrow(()-> new ResourceNotFoundException("book","isbn",id));
        return this.modelMapper.map(book,BookResponse.class);
    }

    public List<BookResponse> getBookAllBook() {
        List<Book> books = this.bookRepo.findAll();
        List<BookResponse> bookDtos = books.stream().map(
                book -> this.modelMapper.map(book,BookResponse.class)
                ).collect(Collectors.toList());
        return bookDtos;
    }

    public void deleteBook(String isbn) {
        Book book = this.bookRepo.findByIsbn(isbn).orElseThrow(()-> new ResourceNotFoundException("book","isbn",isbn));
        this.bookRepo.delete(book);
    }

    public List<BookResponse> getBookByAuthor(String authorId) {
        List<Book> books = this.bookRepo.findByAuthorId(authorId);
        List<BookResponse>  bookResponses = books.stream().map(
                book -> this.modelMapper.map(book,BookResponse.class)
        ).collect(Collectors.toList());
        return bookResponses;
    }
}
