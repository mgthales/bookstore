package com.bookstore.jpa.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthorDto(@NotBlank(message = "O campo 'author' não pode ser vazio ou nulo.")
                        String author) {

}
