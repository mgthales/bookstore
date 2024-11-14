package com.bookstore.jpa.controller;


import com.bookstore.jpa.dto.BookRecordDto;
import com.bookstore.jpa.models.BookModel;
import com.bookstore.jpa.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/bookstore/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookModel>> getAllBooks() {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getAllBooks());
    }

    @PostMapping
    public ResponseEntity<BookModel> saveBook(@RequestBody BookRecordDto bookRecordDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.saveBook(bookRecordDto));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteBook(@PathVariable UUID id) {
        try {
            bookService.findById(id).orElseThrow(() -> new NoSuchElementException("Book with ID " + id + " not found."));
            bookService.deleteBook(id);
            return ResponseEntity.status(HttpStatus.OK).body("Book deleted successfully.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the book.");
        }
    }


}
