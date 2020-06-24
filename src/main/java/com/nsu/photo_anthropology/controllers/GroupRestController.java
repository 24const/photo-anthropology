package com.nsu.photo_anthropology.controllers;

import com.nsu.photo_anthropology.entities.Groups;
import com.nsu.photo_anthropology.entities.Tags;
import com.nsu.photo_anthropology.repositories.GroupRepository;
import com.nsu.photo_anthropology.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/groups")
@RestController
public class GroupRestController {

    private final GroupRepository groupRepository;

    private final TagRepository tagRepository;

    @Autowired
    public GroupRestController(GroupRepository groupRepository, TagRepository tagRepository) {
        this.groupRepository = groupRepository;
        this.tagRepository = tagRepository;
    }

    @GetMapping("/all")
    public Iterable<Groups> getAllGroups() {
        return groupRepository.findAll();
    }

    @GetMapping("/getGroup/id/{id}")
    public Groups getGroupById(@PathVariable("id") long id) {
        return groupRepository.findById(id).get();
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") long id) {
        try {
            groupRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Group was successfully deleted.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no group with id = " + id + ".");
        }
    }

    @PutMapping("/update/id/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") long id, @RequestBody Groups groups) {
        try {
            groupRepository.deleteById(id);
            groups.setId(id);
            groupRepository.save(groups);
            return ResponseEntity.status(HttpStatus.OK).body("Group was successfully updated.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no group with id = " + id + ".");
        }
    }

    @PostMapping("/save")
    @Transactional
    public ResponseEntity<?> saveNewGroup(@RequestBody Groups groups) {
        try {
            groupRepository.save(groups);
            System.out.println(groups.getTags());
            for (Tags tag : groups.getTags()) {
                tag.setGroups(groups);
                tagRepository.save(tag);
            }
            return ResponseEntity.status(HttpStatus.OK).body("Group was successfully saved.");
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
