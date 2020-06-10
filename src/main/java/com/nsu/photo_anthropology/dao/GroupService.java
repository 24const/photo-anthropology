package com.nsu.photo_anthropology.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GroupService {
    @Autowired GroupRepository repo;

    public void save(Groups group) {
        repo.save(group);
    }

    public List<Groups> listAll() {
        return (List<Groups>) repo.findAll();
    }

    public Groups get(Long id) {
        return repo.findById(id).get();
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
