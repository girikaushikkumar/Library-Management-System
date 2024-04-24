package com.library.controller;

import com.library.dto.AuthorDto;
import com.library.payload.ApiResponse;
import com.library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/author/")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @PostMapping("addAuthor")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
        AuthorDto authorDto1 = this.authorService.createAuthor(authorDto);
        return new ResponseEntity<>(authorDto1, HttpStatus.CREATED);
    }

    @PutMapping("updateAuthor")
    public ResponseEntity<ApiResponse> updateAuthor(@RequestBody AuthorDto authorDto,@RequestParam String id) {
        this.authorService.updateAuthor(id,authorDto);
        return new ResponseEntity<>(new ApiResponse("updated successfully"),HttpStatus.OK);
    }

    @GetMapping("getAuthorById/{id}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable String id) {
        AuthorDto authorDto = this.authorService.getAuthorById(id);
        return new ResponseEntity<>(authorDto,HttpStatus.FOUND);
    }

    @GetMapping("getAllAuthor")
    public ResponseEntity<List<AuthorDto>> getAllAuthor() {
        List<AuthorDto> authorDtos = this.authorService.getAllAuthor();
        return new ResponseEntity<>(authorDtos,HttpStatus.FOUND);
    }

    @DeleteMapping("removeAuthor/{id}")
    public ResponseEntity<ApiResponse> deleteAuthor(@PathVariable String id) {
        this.authorService.deleteAuthorById(id);
        return new ResponseEntity<>(new ApiResponse("author remove successfully"),HttpStatus.OK);
    }
}
