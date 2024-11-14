package com.bookstore.jpa.dto;

import java.util.Set;
import java.util.UUID;

public record AuthorDto(String author, Set<UUID> books) {

}
