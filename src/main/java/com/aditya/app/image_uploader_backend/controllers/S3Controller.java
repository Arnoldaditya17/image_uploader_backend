package com.aditya.app.image_uploader_backend.controllers;

import com.aditya.app.image_uploader_backend.services.ImageUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/s3")
public class S3Controller {
    @Autowired
    private ImageUploader imageUploader;

    @PostMapping
    public ResponseEntity<String> upload(@RequestParam MultipartFile file) {
        return ResponseEntity.ok(imageUploader.uploadImage(file));

    }

    @GetMapping
    public List<String> getAllFiles() {
        return imageUploader.allFile();
    }

    @GetMapping("{fileName}")
    public  String urlByName(@PathVariable ("fileName") String fileName)
    {
        return imageUploader.getUrlByFileName(fileName);
    }

//    @DeleteMapping
//    public ResponseEntity<String> deleteFile(@RequestParam MultipartFile file) {
//
//    }




}
