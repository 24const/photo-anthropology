package com.nsu.photo_anthropology.controllers;

import com.nsu.photo_anthropology.entities.Groups;
import com.nsu.photo_anthropology.entities.Images;
import com.nsu.photo_anthropology.entities.Tags;
import com.nsu.photo_anthropology.repositories.GroupRepository;
import com.nsu.photo_anthropology.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/tags")
@RestController
public class TagRestController {

    private final TagRepository tagRepository;

    @Autowired
    public TagRestController(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @GetMapping("/getTag/id/{id}")
    public Tags getTagById(@PathVariable("id") long id) {
        return tagRepository.findById(id).get();
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveNewTag(@RequestBody Groups groups, @RequestBody Tags tags) {
        try {
            tags.setGroups(groups);
            tagRepository.save(tags);
            return ResponseEntity.status(HttpStatus.OK).body("Tag was successfully saved.");
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/id/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") long id, @RequestBody Groups groups, @RequestBody Tags tags) {
        try {
            tagRepository.deleteById(id);
            tags.setId(id);
            tags.setGroups(groups);
            tagRepository.save(tags);
            return ResponseEntity.status(HttpStatus.OK).body("Tag was successfully updated.");
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable("id") long id) {
        try {
            tagRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Tag was successfully deleted.");
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
