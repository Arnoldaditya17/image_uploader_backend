package com.aditya.app.image_uploader_backend.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3config {

    @Value("${cloud.aws.credentials.access-key}")
    private String awsAccessKeyId;
    @Value("${cloud.aws.credentials.secret-key}")
    private String awsSecretAccessKey;
    @Value("${app.s3.bucket}")
    private String bucketName;
    @Value("${cloud.aws.region.static}")
    private String bucketRegion;
    @Bean
    public AmazonS3 client()
    {
        AWSCredentials awsCredentials = new BasicAWSCredentials(awsAccessKeyId, awsSecretAccessKey);

    }


}
