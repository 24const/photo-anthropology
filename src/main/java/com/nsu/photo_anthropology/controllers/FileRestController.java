package com.nsu.photo_anthropology.controllers;

import com.nsu.photo_anthropology.entities.Files;
import com.nsu.photo_anthropology.entities.Images;
import com.nsu.photo_anthropology.repositories.FileRepository;
import com.nsu.photo_anthropology.repositories.ImageRepository;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/files")
public class FileRestController {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private ImageRepository imageRepository;

    @RequestMapping(value = "/getAllFiles", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<Iterable<Files>> getAllFiles() {
        return ResponseEntity.ok(fileRepository.findAll());
    }

    @GetMapping("testFileSave")
    @Transactional
    public String test() {
        JSONArray columnInfo = new JSONArray();
        columnInfo.add("column1");
        columnInfo.add("column2");
        columnInfo.add("column3");
        Files file = new Files("TestFile.csv", columnInfo);
        file.setDate_created(LocalDateTime.now());
        Images images1 = new Images(file, "https://image1.jpg", columnInfo);
        Images images2 = new Images(file, "https://image2.jpg", columnInfo);
        fileRepository.save(file);
        imageRepository.save(images1);
        imageRepository.save(images2);
        return "File was saved successfully";
    }
}
