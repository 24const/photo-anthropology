package com.nsu.photo_anthropology.services;

import com.nsu.photo_anthropology.entities.Groups;
import com.nsu.photo_anthropology.entities.Tags;
import com.nsu.photo_anthropology.repositories.GroupRepository;
import com.nsu.photo_anthropology.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TagService {
    @Autowired
    TagRepository repo;

    public void save(Tags tag) {
        repo.save(tag);
    }

    public List<Tags> listAll() {
        return (List<Tags>) repo.findAll();
    }

    public Tags get(Long id) {
        return repo.findById(id).get();
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
