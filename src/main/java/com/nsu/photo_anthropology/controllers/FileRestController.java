package com.nsu.photo_anthropology.controllers;

import com.nsu.photo_anthropology.entities.Files;
import com.nsu.photo_anthropology.entities.Images;
import com.nsu.photo_anthropology.file_workers.FileParser;
import com.nsu.photo_anthropology.file_workers.FileReadingWorker;
import com.nsu.photo_anthropology.repositories.FileRepository;
import com.nsu.photo_anthropology.repositories.ImageRepository;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RequestMapping("/files")
@RestController
public class FileRestController {

    private final FileRepository fileRepository;
    private final ImageRepository imageRepository;
    private String absoluteFolderPath = "C:\\Users\\Ksenia\\Desktop\\photo-anthropology\\target\\";

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
        try {
            fileRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("File was successfully deleted.");
        } catch (Exception e) {
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
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no file with id = " + id + ".");
        }
    }

    @PostMapping("/save")
    public void saveNewFile(@RequestParam("uploadedFile") MultipartFile uploadFile) throws IOException {
        if (!uploadFile.isEmpty()) {
            List<String> uploadedFilesPath = saveUploadedFiles(Arrays.asList(uploadFile));
            for (String filePath : uploadedFilesPath) {

                String absoluteFilePath = absoluteFolderPath + filePath;
                Map<Integer, List<String>> data = FileParser.readXLSXFile(absoluteFilePath);
                JSONArray jsonArray = new JSONArray();
                jsonArray.addAll(data.remove(0));

                Files files = new Files(filePath, jsonArray);
                FileReadingWorker fileReadingWorker = new FileReadingWorker(files, data);
                files.setDate_created(LocalDateTime.now());
                fileRepository.save(files);

                for (Images image : fileReadingWorker.getImages()) {
                    image.setFiles(files);
                    imageRepository.save(image);
                }
            }
        }
    }

    private List<String> saveUploadedFiles(List<MultipartFile> files) throws IOException {
        List<String> filesPath = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }
            byte[] bytes = file.getBytes();
            Path path = Paths.get(absoluteFolderPath + file.getOriginalFilename());
            String uploadedFilePath = absoluteFolderPath + file.getOriginalFilename();
            new File(uploadedFilePath);
            filesPath.add(file.getOriginalFilename());
            java.nio.file.Files.write(path, bytes);
        }
        return filesPath;
    }
}
