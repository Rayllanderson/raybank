package com.rayllanderson.raybank.core.services;

import com.rayllanderson.raybank.users.model.ProfilePicture;

import java.time.Instant;

public record S3UploadOutput(String key, String url, Instant expiration) {

    public ProfilePicture toProfilePicture() {
        return new ProfilePicture(key(), null, url(), null, expiration());
    }
}
