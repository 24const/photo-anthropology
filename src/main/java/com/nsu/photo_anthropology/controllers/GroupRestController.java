package com.nsu.photo_anthropology.controllers;

import com.nsu.photo_anthropology.entities.Groups;
import com.nsu.photo_anthropology.repositories.GroupRepository;
import com.nsu.photo_anthropology.structure_entities.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GroupRestController {

    @Autowired
    private GroupRepository groupRepository;

    @GetMapping("/testSave")
    public String test() {
        Groups group = new Groups("Ksenia Group", "Do you like dogs?");

        groupRepository.save(group);
        return "Group was saved successfully";
    }

    @GetMapping("/testGet")
    public String testGet() {

        Iterable<Groups> groupsList = groupRepository.findAll();

        String groupStr = "";
        for (Groups s : groupsList) {
            groupStr += " " + s;
        }

        return groupStr;

    }
}
