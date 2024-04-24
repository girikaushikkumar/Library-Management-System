package com.library.controller;

import com.library.dto.BookDto;
import com.library.dto.BookResponse;
import com.library.model.Book;
import com.library.payload.ApiResponse;
import com.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/book/")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("addBook/{authorId}")
    public ResponseEntity<ApiResponse> addBook(@RequestBody BookDto bookDto,@PathVariable String authorId) {
        ApiResponse apiResponse = this.bookService.addBook(bookDto,authorId);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PutMapping("updateBook/{isbn}")
    public ResponseEntity<ApiResponse> updateBook(@RequestBody BookDto bookDto,@PathVariable String isbn) {
        this.bookService.UpdateBook(bookDto,isbn);
        return new ResponseEntity<>(new ApiResponse("updated successfully"),HttpStatus.OK);
    }

    @GetMapping("getBookByIsbn/{isbn}")
    public ResponseEntity<BookResponse> getBookByIsbn(@PathVariable String isbn) {
        BookResponse book = this.bookService.getBookById(isbn);
        return new ResponseEntity<>(book,HttpStatus.FOUND);
    }

    @GetMapping("getAllbooks")
    public ResponseEntity<List<BookResponse>> getAllBook() {
        List<BookResponse> books = this.bookService.getBookAllBook();
        return new ResponseEntity<>(books,HttpStatus.OK);
    }
    @DeleteMapping("removeBook/{isbn}")
    public ResponseEntity<ApiResponse> removeBook(@PathVariable String isbn) {
        this.bookService.deleteBook(isbn);
        return new ResponseEntity<>(new ApiResponse("remove successfully"),HttpStatus.OK);
    }

    @GetMapping("getBooksByAuthor/{authorId}")
    public ResponseEntity<List<BookResponse>> getBooksByAuthor(@PathVariable String authorId) {
        List<BookResponse> bookResponses = this.bookService.getBookByAuthor(authorId);
        return new ResponseEntity<>(bookResponses,HttpStatus.FOUND);
    }
}
