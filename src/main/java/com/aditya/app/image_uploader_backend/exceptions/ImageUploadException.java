package com.aditya.app.image_uploader_backend.exceptions;

import java.io.IOException;

public class ImageUploadException extends RuntimeException{
    public ImageUploadException(String message) {
        super(message);
    }
}
