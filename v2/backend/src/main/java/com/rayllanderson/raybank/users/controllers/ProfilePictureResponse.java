package com.rayllanderson.raybank.users.controllers;

import com.rayllanderson.raybank.core.services.S3PresignUrlOutput;
import com.rayllanderson.raybank.core.services.S3UploadOutput;
import com.rayllanderson.raybank.users.services.profile.FindProfileOutput;

import java.time.Instant;

public record ProfilePictureResponse(
        String url,
        Instant expiration
) {

    public static ProfilePictureResponse from(final FindProfileOutput findProfileOutput) {
        return new ProfilePictureResponse(findProfileOutput.preSignedUrl(), findProfileOutput.expiration());
    }

    public static ProfilePictureResponse from(final S3PresignUrlOutput s3PresignUrlOutput) {
        return new ProfilePictureResponse(s3PresignUrlOutput.url().toString(), s3PresignUrlOutput.expiration());
    }

    public static ProfilePictureResponse from(final S3UploadOutput s3UploadOutput) {
        return new ProfilePictureResponse(s3UploadOutput.url(), s3UploadOutput.expiration());
    }
}
