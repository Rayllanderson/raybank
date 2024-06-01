package com.rayllanderson.raybank.core.services;

import io.awspring.cloud.s3.S3Operations;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.UUID;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class S3Service {

    @Value("${s3.bucketName}")
    private String bucketName;
    private final S3Operations s3Operations;
    private final S3Presigner s3Presigner;
    public static final int PRE_SIGN_EXPIRATION_HOURS = 28;

    public String uploadFile(final MultipartFile file) throws IOException {
        final String objectKey = "uploads/" + UUID.randomUUID() + "." + file.getContentType().split("/")[1];

        s3Operations.upload(bucketName, objectKey, file.getInputStream());

        URL url = this.generatePresignedUrl(objectKey);
        return url.toString();
    }

    public URL generatePresignedUrl(final String objectKey) {
        Consumer<PutObjectRequest.Builder> putObjectRequest = builder -> builder
                .bucket(bucketName)
                .key(objectKey)
                .build();

        return s3Presigner
                .presignPutObject(r -> r.signatureDuration(Duration.ofHours(PRE_SIGN_EXPIRATION_HOURS))
                .putObjectRequest(putObjectRequest))
                .url();
    }
}