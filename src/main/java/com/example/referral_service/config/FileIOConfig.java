package com.example.referral_service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.net.URI;

@Configuration
public class FileIOConfig {
    private final String region;
    private final String endpointOverride;
    private final String accessKey;
    private final String secretKey;

    @Autowired
    public FileIOConfig(
            @Value("${s3.region}") String region,
            @Value("${s3.endpointOverride}") String endpointOverride,
            @Value("${s3.accessKey}") String accessKey,
            @Value("${s3.secretKey}") String secretKey
    ) {
        this.region = region;
        this.endpointOverride = endpointOverride;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of(region))
                .endpointOverride(URI.create(endpointOverride))
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
    }

    @Bean
    public S3Presigner s3Presigner() {
        return S3Presigner.builder()
                .region(Region.of(region))
                .endpointOverride(URI.create(endpointOverride))
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
    }
}
