package com.example.referral_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

@Service
public class FileIOService {
    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    private final String s3bucketName;

    public FileIOService(
            S3Client s3Client,
            S3Presigner s3Presigner,
            @Value("${s3.bucketName}") String s3bucketName
    ) {
        this.s3Client = s3Client;
        this.s3Presigner = s3Presigner;
        this.s3bucketName = s3bucketName;
    }

    private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File convertedFile = File.createTempFile("upload-", multipartFile.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(multipartFile.getBytes());
        }
        return convertedFile;
    }

    public CompletableFuture<Boolean> uploadFile(String key, MultipartFile file) {
        return CompletableFuture.supplyAsync(() -> {
                File tempFile = null;
                try {
                    tempFile = convertMultipartFileToFile(file);
                    s3Client.putObject(
                            PutObjectRequest.builder()
                                    .bucket(s3bucketName)
                                    .key(key)
                                    .build(),
                            RequestBody.fromFile(tempFile)
                    );
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                } finally {
                    if (tempFile != null && tempFile.exists()) {
                        tempFile.delete();
                    }
                }
        });
    }

    public String generatePresignedUrl(String key) {
        PresignedGetObjectRequest presignedRequest = s3Presigner.presignGetObject(b -> b
                .getObjectRequest(builder -> builder.bucket(s3bucketName).key(key))
                .signatureDuration(Duration.ofHours(1))
        );
        return presignedRequest.url().toString();
    }

    // TODO: Add delete operation, and then check for existing file and delete and then upload again to replace
}
