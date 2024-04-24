package com.library.service;

import com.library.dto.AuthorDto;
import com.library.model.Author;
import com.library.repository.AuthorRepo;
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
public class AuthorServiceTest {

    @Mock
    private AuthorRepo authorRepo;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private AuthorService authorService;
    private Author author;
    private AuthorDto authorDto;

    @BeforeEach
    void setUp() {
        author = new Author("1", "John Doe", "Biography of John Doe");
        authorDto = new AuthorDto("1", "John Doe", "Biography of John Doe");
    }

    @Test
    void CreateAuthorTest() {
        when(modelMapper.map(authorDto, Author.class)).thenReturn(author);
        when(modelMapper.map(author, AuthorDto.class)).thenReturn(authorDto);
        when(authorRepo.save(author)).thenReturn(author);

        AuthorDto createdAuthorDto = authorService.createAuthor(authorDto);

        assertNotNull(createdAuthorDto);
        assertEquals(authorDto, createdAuthorDto);
        verify(authorRepo, times(1)).save(author);
    }

    @Test
    void UpdateAuthorTest() {
        when(authorRepo.findById("1")).thenReturn(Optional.of(author));
        authorService.updateAuthor("1",authorDto);
        assertEquals("John Doe",author.getName());
        assertEquals("Biography of John Doe",author.getBiography());
        verify(authorRepo,times(1)).save(author);
    }

    @Test
    void GetAuthorByIdTest() {
        when(authorRepo.findById("1")).thenReturn(Optional.of(author));
        when(modelMapper.map(author, AuthorDto.class)).thenReturn(authorDto);

        AuthorDto retrievedAuthorDto = authorService.getAuthorById("1");

        assertNotNull(retrievedAuthorDto);
        assertEquals(authorDto, retrievedAuthorDto);
    }

    @Test
    void GetAllAuthorTest() {
        List<Author> authors = new ArrayList<>();
        authors.add(author);

        when(authorRepo.findAll()).thenReturn(authors);
        when(modelMapper.map(author, AuthorDto.class)).thenReturn(authorDto);

        List<AuthorDto> retrievedAuthorDtos = authorService.getAllAuthor();

        assertNotNull(retrievedAuthorDtos);
        assertEquals(1, retrievedAuthorDtos.size());
        assertEquals(authorDto, retrievedAuthorDtos.get(0));
    }

    @Test
    void deleteAuthorByIdTest() {
        when(authorRepo.findById("1")).thenReturn(Optional.of(author));
        authorService.deleteAuthorById("1");
        verify(authorRepo,times(1)).delete(author);
    }
}
