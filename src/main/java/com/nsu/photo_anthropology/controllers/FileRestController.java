package com.nsu.photo_anthropology.controllers;

import com.nsu.photo_anthropology.entities.Files;
import com.nsu.photo_anthropology.entities.Images;
import com.nsu.photo_anthropology.repositories.FileRepository;
import com.nsu.photo_anthropology.repositories.ImageRepository;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequestMapping("/files")
@RestController
public class FileRestController {

    private final FileRepository fileRepository;

    private final ImageRepository imageRepository;

    @Autowired
    public FileRestController(FileRepository fileRepository, ImageRepository imageRepository) {
        this.fileRepository = fileRepository;
        this.imageRepository = imageRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<Files>> getAll() {
        return ResponseEntity.ok(fileRepository.findAll());
    }

    @GetMapping("/getFile/id/{id}")
    public Files getFileById(@PathVariable("id") long id) {
        return fileRepository.findById(id).get();
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") long id) {
        try{
            fileRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("File was successfully deleted.");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no file with id = " + id + ".");
        }
    }

    @PutMapping("/update/id/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") long id, @RequestBody Files files) {
        try {
            fileRepository.deleteById(id);
            files.setId(id);
            fileRepository.save(files);
            return ResponseEntity.status(HttpStatus.OK).body("File was successfully updated.");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no file with id = " + id + ".");
        }
    }

    @PostMapping("/save")
    @Transactional
    public ResponseEntity<?> saveNewFile(@RequestBody Files files, @RequestBody List<Images> images) {
        try {
            files.setDate_created(LocalDateTime.now());
            fileRepository.save(files);
            for(Images image:images){
                image.setFiles(files);
                imageRepository.save(image);
            }
            return ResponseEntity.status(HttpStatus.OK).body("File was successfully saved.");
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/testFileSave")
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
