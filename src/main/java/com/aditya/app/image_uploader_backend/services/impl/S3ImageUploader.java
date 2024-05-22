package com.aditya.app.image_uploader_backend.services.impl;

import com.aditya.app.image_uploader_backend.exceptions.ImageUploadException;
import com.aditya.app.image_uploader_backend.services.ImageUploader;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class S3ImageUploader implements ImageUploader {

    @Autowired
    private  AmazonS3 client;

    @Value("${app.s3.bucket}")
    private  String bucketName;




    @Override
    public String uploadImage(MultipartFile file) {
        String actualFileName = file.getOriginalFilename();

        assert actualFileName != null;




        String fileName = UUID.randomUUID().toString()+actualFileName.substring(actualFileName.lastIndexOf("."));
        ObjectMetadata metaData = new ObjectMetadata();
        //metadata.setContentType("image/jpeg");
        metaData.setContentLength(file.getSize());
        try {
            PutObjectResult putObjectResult = client.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(),metaData));
            return this.preSignedUrl(fileName);
        } catch (IOException e) {
            throw new ImageUploadException("error in uploading image"+ e.getMessage());
        }
    }

    @Override
    public List<String> allFile() {
        ListObjectsV2Request listObjectsV2Request = new ListObjectsV2Request().withBucketName(bucketName);

       ListObjectsV2Result listObjectsV2Result= client.listObjectsV2(listObjectsV2Request);
       List<S3ObjectSummary> s3ObjectSummaries = listObjectsV2Result.getObjectSummaries();
      List<String> listOfFileUrls =  s3ObjectSummaries.stream().map(item -> this.preSignedUrl(item.getKey())).collect(Collectors.toList());
        return listOfFileUrls;
    }



    @Override
    public String preSignedUrl(String fileName) {

        Date expiration = new Date();
        long time = expiration.getTime();
        int hour =15;
        time = time + hour*60*60*1000;
        expiration.setTime(time);

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileName)
                .withMethod(HttpMethod.GET)
                .withExpiration(expiration);
       URL url= client.generatePresignedUrl(generatePresignedUrlRequest);
       return url.toString();

    }

    @Override
    public String getUrlByFileName(String fileName) {


        S3Object s3Object = client.getObject(bucketName, fileName);
        String key= s3Object.getKey();
        String url=this.preSignedUrl(key);
                return url;
    }
}
