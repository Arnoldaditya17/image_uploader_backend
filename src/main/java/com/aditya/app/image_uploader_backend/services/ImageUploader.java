package com.aditya.app.image_uploader_backend.services;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageUploader {

    String uploadImage(MultipartFile file);

  List<String> allFile();

  String preSignedUrl(String fileName);

  String getUrlByFileName(String fileName);



}
