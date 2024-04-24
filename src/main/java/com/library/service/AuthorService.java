package com.library.service;

import com.library.dto.AuthorDto;
import com.library.exception.ResourceNotFoundException;
import com.library.model.Author;
import com.library.repository.AuthorRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepo authorRepo;
    @Autowired
    private ModelMapper modelMapper;

    public AuthorDto createAuthor(AuthorDto authorDto) {
        Author author = this.modelMapper.map(authorDto,Author.class);
        AuthorDto authorDto1 = this.modelMapper.map(this.authorRepo.save(author),AuthorDto.class);
        return authorDto1;
    }

    public void updateAuthor(String id,AuthorDto authorDto) {
        Author author = this.authorRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Author","id",id));
        author.setName(authorDto.getName());
        author.setBiography(authorDto.getBiography());
        this.authorRepo.save(author);
    }

    public AuthorDto getAuthorById(String id) {
        Author author = this.authorRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Author","id",id));
        AuthorDto authorDto = this.modelMapper.map(author,AuthorDto.class);
        return authorDto;
    }

    public List<AuthorDto> getAllAuthor() {
        List<Author> authors = this.authorRepo.findAll();
        List<AuthorDto> authorDtos = authors.stream().map(
                (author)->this.modelMapper.map(author,AuthorDto.class)
        ).collect(Collectors.toList());
        return authorDtos;
    }

    public void deleteAuthorById(String id) {
        Author author = this.authorRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Author","id",id));
        this.authorRepo.delete(author);
    }
}
