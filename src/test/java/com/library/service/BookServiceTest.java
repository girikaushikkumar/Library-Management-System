package com.library.service;

import com.library.dto.BookDto;
import com.library.dto.BookResponse;
import com.library.model.Author;
import com.library.model.Book;
import com.library.payload.ApiResponse;
import com.library.repository.AuthorRepo;
import com.library.repository.BookRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepo bookRepo;

    @Mock
    private AuthorRepo authorRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BookService bookService;

    private Book book;
    private BookDto bookDto;
    private Author author;

    @BeforeEach
    void setUp() {
        book = new Book("1","978-3-16-148410-0",null, "Title", 2022);
        bookDto = new BookDto("1", "Title", "978-3-16-148410-0", 2022);
        author = new Author("1", "John Doe", "Biography of John Doe");
    }

    @Test
    void testAddBook_Success() {
        when(bookRepo.existsByIsbn("978-3-16-148410-0")).thenReturn(false);
        when(modelMapper.map(bookDto, Book.class)).thenReturn(book);
        when(authorRepo.findById("1")).thenReturn(Optional.of(author));

        ApiResponse response = bookService.addBook(bookDto, "1");

        assertNotNull(response);
        assertEquals("book added successfully", response.getMessage());
        verify(bookRepo, times(1)).save(book);
    }

    @Test
    void testAddBook_BookAlreadyExists() {
        when(bookRepo.existsByIsbn("978-3-16-148410-0")).thenReturn(true);

        ApiResponse response = bookService.addBook(bookDto, "1");

        assertNotNull(response);
        assertEquals("book already exit", response.getMessage());
        verify(bookRepo, never()).save(any());
    }

    @Test
    void testUpdateBook() {
        when(bookRepo.findByIsbn("978-3-16-148410-0")).thenReturn(Optional.of(book));
        BookDto updatedBookDto = new BookDto("1", "Updated Title", "978-3-16-148410-0", 2023);

        bookService.UpdateBook(updatedBookDto, "978-3-16-148410-0");

        assertEquals("Updated Title", book.getTitle());
        assertEquals(2023, book.getPublicationYear());
        verify(bookRepo, times(1)).save(book);
    }

    @Test
    void testGetBookById() {
        when(bookRepo.findByIsbn("978-3-16-148410-0")).thenReturn(Optional.of(book));
        BookResponse expectedResponse = new BookResponse("1", "Title", null, "978-3-16-148410-0", 2022);
        when(modelMapper.map(book, BookResponse.class)).thenReturn(expectedResponse);

        BookResponse response = bookService.getBookById("978-3-16-148410-0");

        assertNotNull(response);
        assertEquals(expectedResponse, response);
    }

    @Test
    void testGetBookAllBook() {
        List<Book> books = new ArrayList<>();
        books.add(book);
        when(bookRepo.findAll()).thenReturn(books);
        BookResponse expectedResponse = new BookResponse("1", "Title", null, "978-3-16-148410-0", 2022);
        when(modelMapper.map(book, BookResponse.class)).thenReturn(expectedResponse);

        List<BookResponse> responses = bookService.getBookAllBook();

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals(expectedResponse, responses.get(0));
    }

    @Test
    void testDeleteBook() {
        when(bookRepo.findByIsbn("978-3-16-148410-0")).thenReturn(Optional.of(book));

        bookService.deleteBook("978-3-16-148410-0");

        verify(bookRepo, times(1)).delete(book);
    }

    @Test
    void testGetBookByAuthor() {
        List<Book> books = new ArrayList<>();
        books.add(book);
        when(bookRepo.findByAuthorId("1")).thenReturn(books);
        BookResponse expectedResponse = new BookResponse("1", "Title", null, "978-3-16-148410-0", 2022);
        when(modelMapper.map(book, BookResponse.class)).thenReturn(expectedResponse);

        List<BookResponse> responses = bookService.getBookByAuthor("1");

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals(expectedResponse, responses.get(0));
    }
}
