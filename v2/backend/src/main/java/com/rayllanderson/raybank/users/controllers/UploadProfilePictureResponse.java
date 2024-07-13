package com.rayllanderson.raybank.users.controllers;

import com.rayllanderson.raybank.core.services.S3UploadOutput;

import java.time.Instant;

public record UploadProfilePictureResponse(
        String preSignedUrl,
        Instant expiration
) {

    public static UploadProfilePictureResponse from(final S3UploadOutput s3UploadOutput) {
        return new UploadProfilePictureResponse(s3UploadOutput.url(), s3UploadOutput.expiration());
    }
}
