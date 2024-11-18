package com.bookstore.jpa.controller;

import com.bookstore.jpa.dto.PublisherDto;
import com.bookstore.jpa.models.PublisherModel;
import com.bookstore.jpa.repository.PublisherRepository;
import com.bookstore.jpa.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/bookstore/publisher")
public class PublisherController {

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private PublisherService publisherService;

    @GetMapping
    public ResponseEntity<List<PublisherModel>> getAllPublishers(){
        List<PublisherModel> publishers = publisherRepository.findAll();
        return ResponseEntity.ok(publishers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPublisher(@PathVariable UUID id) {
        Optional<PublisherModel> publisher = publisherService.findByIdPublisher(id);

        if (publisher.isPresent()) {
            return ResponseEntity.ok(publisher.get());
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Não existe editora com esse ID = " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }


    @PostMapping
    public ResponseEntity<Object> addPublisherDto(@RequestBody PublisherDto publisher){
        try {
            PublisherDto savedPublisher = publisherService.addPublisherDto(publisher);
            return ResponseEntity.ok(savedPublisher);
        }catch  (NoSuchElementException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Erro ao Criar Publisher");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletePublisher(@PathVariable UUID id){
        if (!publisherService.findByIdPublisher(id).isPresent() )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não existe editora com esse ID "+ id);

        try {
            publisherService.deletePublisherDto(id);
            return ResponseEntity.ok().body("Publisher deletada com sucesso");
        }catch (NoSuchElementException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Erro ao deletar publisher");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePublisherDto(
            @PathVariable(value = "id") UUID id,
            @RequestBody PublisherDto publisherDto) {

        Optional<PublisherModel> publisherOptional = publisherService.findByIdPublisher(id);

        if (publisherOptional.isPresent()) {
            PublisherModel publisher = publisherOptional.get();
            publisher.setName(publisherDto.name());
            PublisherModel updatedPublisher = publisherService.savePublisher(publisher);

            return ResponseEntity.ok(updatedPublisher);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Editora nao encontrada com este ID + " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

}
