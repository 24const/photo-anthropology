package com.nsu.photo_anthropology.controllers;

import com.nsu.photo_anthropology.entities.Files;
import com.nsu.photo_anthropology.entities.Groups;
import com.nsu.photo_anthropology.entities.Images;
import com.nsu.photo_anthropology.entities.Tags;
import com.nsu.photo_anthropology.repositories.GroupRepository;
import com.nsu.photo_anthropology.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/groups")
@RestController
public class GroupRestController {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private TagRepository tagRepository;

    @RequestMapping(value = "/all", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<Iterable<Groups>> getAllGroups() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(groupRepository.findAll());
    }

    @RequestMapping(value = "/getGroup/id/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> getGroupById(@PathVariable("id") long id) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(groupRepository.findById(id));
    }

    @RequestMapping(value = "/delete/id/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> deleteById(@PathVariable("id") long id) {
        try{
            groupRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Group was successfully deleted.");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no group with id = " + id + ".");
        }
    }

    @RequestMapping(value = "/update/id/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> updateById(@PathVariable("id") long id, @RequestBody Groups groups) {
        try{
            groupRepository.deleteById(id);
            groups.setId(id);
            groupRepository.save(groups);
            return ResponseEntity.status(HttpStatus.OK).body("Group was successfully updated.");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no group with id = " + id + ".");
        }
    }

    @RequestMapping(value = "/save", method = {RequestMethod.GET, RequestMethod.POST})
    @Transactional
    public ResponseEntity<?> saveNewGroup(@RequestBody Groups groups, @RequestBody List<Tags> tags) {
        try {
            groupRepository.save(groups);
            for(Tags tag:tags){
                tag.setGroups(groups);
                tagRepository.save(tag);
            }
            return ResponseEntity.status(HttpStatus.OK).body("Group was successfully saved.");
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
