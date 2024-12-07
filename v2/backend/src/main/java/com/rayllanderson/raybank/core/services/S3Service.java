package com.rayllanderson.raybank.core.services;

import io.awspring.cloud.s3.S3Operations;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    @Value("${s3.bucketName}")
    private String bucketName;
    private final S3Operations s3Operations;
    private final S3Presigner s3Presigner;
    public static final int PRE_SIGN_EXPIRATION_HOURS = 28;
    private static final int ONE_HOUR_IN_SECONDS = 3600;

    public S3UploadOutput uploadFile(final MultipartFile file) throws IOException {
        final String objectKey = "uploads/" + UUID.randomUUID() + "." + getContentType(file);

        s3Operations.upload(bucketName, objectKey, file.getInputStream());

        S3PresignUrlOutput s3PresignUrlOutput = this.generatePresignedUrl(objectKey);

        return new S3UploadOutput(objectKey, s3PresignUrlOutput.url().toExternalForm(), s3PresignUrlOutput.expiration());
    }

    private static String getContentType(MultipartFile file) {
        return Optional.ofNullable(file.getContentType())
                .map(contentType -> contentType.split("/")[1])
                .orElse("jpg");
    }

    public void deleteFile(final String objectKey) {
        s3Operations.deleteObject(bucketName, objectKey);
    }

    public S3PresignUrlOutput generatePresignedUrl(final String objectKey) {
        GetObjectRequest objectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofHours(PRE_SIGN_EXPIRATION_HOURS))
                .getObjectRequest(objectRequest)
                .build();

        PresignedGetObjectRequest presignedRequest = s3Presigner.presignGetObject(presignRequest);

        return new S3PresignUrlOutput(presignedRequest.url(), presignedRequest.expiration().minusSeconds(ONE_HOUR_IN_SECONDS));

    }
}