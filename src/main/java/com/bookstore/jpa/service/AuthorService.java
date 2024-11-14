package com.bookstore.jpa.service;

import com.bookstore.jpa.dto.AuthorDto;
import com.bookstore.jpa.models.AuthorModel;
import com.bookstore.jpa.repository.AuthorRepository;
import com.bookstore.jpa.repository.BookRepository;
import com.bookstore.jpa.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorService {

    @Autowired
private final AuthorRepository authorRepository;
    @Autowired
private final PublisherRepository publisherRepository;
    @Autowired
private final BookRepository bookRepository;

    public AuthorService(AuthorRepository authorRepository, PublisherRepository publisherRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
        this.bookRepository = bookRepository;
    }

    public List<AuthorModel> getallAuthors() {
        return authorRepository.findAll();
    }

    public AuthorModel getAuthorById(UUID id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Author with ID " + id + " not found"));
    }
    public Optional<AuthorModel> findByIdAuthor(UUID id) {
        return authorRepository.findById(id);
    }

    public AuthorDto save(AuthorDto authorDto) {
        // Converte `AuthorDto` em `Author`
        AuthorModel author = new AuthorModel();
        author.setAuthor(authorDto.author()); // Acessa o campo `author` diretamente

        // Salva o autor
        AuthorModel savedAuthor = authorRepository.save(author);

        // Retorna um novo `AuthorDto` baseado no `Author` salvo
        return new AuthorDto(savedAuthor.getAuthor(), authorDto.books());
    }

    public void deleteAuthorById(UUID id) {
        authorRepository.deleteById(id);
    }

    public Optional<AuthorModel> update(UUID id, AuthorModel authorModel) {
        Optional<AuthorModel> existingAuthor = authorRepository.findById(id);
        if (existingAuthor.isPresent()) {
            // Atualiza os dados do autor existente
            AuthorModel authorToUpdate = existingAuthor.get();
            authorToUpdate.setAuthor(authorModel.getAuthor()); // Exemplo: campos para atualizar

            authorRepository.save(authorToUpdate);
            return Optional.of(authorToUpdate);
        } else {
            return Optional.empty();
        }
    }

}
