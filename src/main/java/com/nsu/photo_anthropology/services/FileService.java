package com.nsu.photo_anthropology.services;

import com.nsu.photo_anthropology.entities.Files;
import com.nsu.photo_anthropology.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FileService {
    @Autowired
    FileRepository repo;

    public void save(Files files) {
        repo.save(files);
    }

    public List<Files> listAll() {
        return (List<Files>) repo.findAll();
    }

    public Files get(Long id) {
        return repo.findById(id).get();
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
