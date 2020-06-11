package com.nsu.photo_anthropology.controllers;

import com.nsu.photo_anthropology.entities.Groups;
import com.nsu.photo_anthropology.entities.Tags;
import com.nsu.photo_anthropology.repositories.GroupRepository;
import com.nsu.photo_anthropology.repositories.TagRepository;
import com.nsu.photo_anthropology.structure_entities.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GroupRestController {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private TagRepository tagRepository;

    @GetMapping("/testSave")
    @Transactional
    public String test() {
        Groups group = new Groups("Ksenia Group", "Do you like dogs?");
        groupRepository.save(group);
        tagRepository.save(new Tags("test_tag1", group));
        tagRepository.save(new Tags("test_tag2", group));
        return "Group was saved successfully";
    }

    @GetMapping("/testGet")
    public String testGet() {

        Iterable<Groups> groupsList = groupRepository.findAll();

        String groupStr = "";
        for (Groups s : groupsList) {
            groupStr += " " + s + ' ' + s.getTags();
        }
        return groupStr;



    }
}
