package com.rayllanderson.raybank.core.services;

import com.rayllanderson.raybank.users.model.ProfilePicture;

import java.time.LocalDateTime;

public record S3UploadOutput(String key, String url, LocalDateTime expiresIn) {

    public ProfilePicture toProfilePicture() {
        return new ProfilePicture(key(), url(), expiresIn());
    }
}
