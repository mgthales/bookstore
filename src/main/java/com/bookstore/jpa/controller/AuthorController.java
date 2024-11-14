package com.bookstore.jpa.controller;

import com.bookstore.jpa.dto.AuthorDto;
import com.bookstore.jpa.models.AuthorModel;
import com.bookstore.jpa.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/bookstore/author")
public class AuthorController {
    private AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }
    @GetMapping
    public ResponseEntity<List<AuthorModel>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(authorService.getallAuthors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAuthorById(@PathVariable UUID id) {
        try {
            AuthorModel author = authorService.getAuthorById(id);
            return ResponseEntity.ok(author);
        } catch (NoSuchElementException e) {
            // Cria um map com a mensagem de erro
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Author not found with ID " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PostMapping
    public ResponseEntity<AuthorDto> save(@RequestBody AuthorDto authorDto) {
        AuthorDto savedAuthor = authorService.save(authorDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAuthor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        if (!authorService.findByIdAuthor(id).isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author not found with ID " + id);
        try {
            authorService.deleteAuthorById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Author deleted successfully");
        }catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the Author.");
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<AuthorModel> update(@PathVariable(value = "id") UUID id, @RequestBody AuthorModel authorModel) {
        Optional<AuthorModel> updatedAuthor = authorService.update(id, authorModel);
        if (updatedAuthor.isPresent()) {
            return ResponseEntity.ok(updatedAuthor.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
