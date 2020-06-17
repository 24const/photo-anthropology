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

    @Autowired
    private ImageRepository imageRepository;

    @RequestMapping(value = "/getImage/id/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<Optional<Images>> getImageById(@PathVariable("id") long id) {
        return ResponseEntity.ok(imageRepository.findById(id));
    }

    @RequestMapping(value = "/save", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> saveNewImage(@RequestBody Files files, @RequestBody Images images) {
        try {
            images.setFiles(files);
            imageRepository.save(images);
            return ResponseEntity.status(HttpStatus.OK).body("Image was successfully saved.");
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/update/id/{id}", method = {RequestMethod.GET, RequestMethod.POST})
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

    @RequestMapping(value = "/delete/id/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> deleteImage(@PathVariable("id") long id) {
        try {
            imageRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Image was successfully deleted.");
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
