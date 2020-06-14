package com.nsu.photo_anthropology.services;

import com.nsu.photo_anthropology.entities.Images;
import com.nsu.photo_anthropology.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ImageService {
    @Autowired
    ImageRepository repo;

    public void save(Images images) {
        repo.save(images);
    }

    public List<Images> listAll() {
        return (List<Images>) repo.findAll();
    }

    public Images get(Long id) {
        return repo.findById(id).get();
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
