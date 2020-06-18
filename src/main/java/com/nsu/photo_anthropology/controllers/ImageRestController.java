package com.nsu.photo_anthropology.controllers;

import com.nsu.photo_anthropology.entities.Files;
import com.nsu.photo_anthropology.entities.Images;
import com.nsu.photo_anthropology.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/images")
@RestController
public class ImageRestController {

    private final ImageRepository imageRepository;
    @Autowired
    public ImageRestController(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @GetMapping("/getImage/id/{id}")
    public ResponseEntity<Optional<Images>> getImageById(@PathVariable("id") long id) {
        return ResponseEntity.ok(imageRepository.findById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveNewImage(@RequestBody Files files, @RequestBody Images images) {
        try {
            images.setFiles(files);
            imageRepository.save(images);
            return ResponseEntity.status(HttpStatus.OK).body("Image was successfully saved.");
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/id/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") long id, @RequestBody Files files, @RequestBody Images images) {
        try {
            imageRepository.deleteById(id);
            images.setId(id);
            images.setFiles(files);
            imageRepository.save(images);
            return ResponseEntity.status(HttpStatus.OK).body("Image was successfully updated.");
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<?> deleteImage(@PathVariable("id") long id) {
        try {
            imageRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Image was successfully deleted.");
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
