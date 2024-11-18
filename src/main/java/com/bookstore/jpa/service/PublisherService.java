package com.bookstore.jpa.service;

import com.bookstore.jpa.dto.PublisherDto;
import com.bookstore.jpa.models.AuthorModel;
import com.bookstore.jpa.models.PublisherModel;
import com.bookstore.jpa.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PublisherService {
    @Autowired
    private PublisherRepository publisherRepository;

    public PublisherDto addPublisherDto(PublisherDto publisherDto) {
        PublisherModel publisherModel = new PublisherModel();
        publisherModel.setName(publisherDto.name());

        PublisherModel savedPublisher = publisherRepository.save(publisherModel);
        return new PublisherDto(savedPublisher.getName());
    }

    public Optional<PublisherModel> findByIdPublisher(UUID id) {
        return publisherRepository.findById(id);
    }

    public PublisherModel savePublisher(PublisherModel publisher) {
        return publisherRepository.save(publisher);
    }

public void deletePublisherDto(UUID id) {
        publisherRepository.deleteById(id);
        }


}
