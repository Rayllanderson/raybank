package com.rayllanderson.raybank.users.services.profile;

import com.rayllanderson.raybank.users.model.User;

import java.time.Instant;

public record FindProfileOutput(
        String key,
        String preSignedUrl,
        Instant expiration
) {

    public static FindProfileOutput from(User user) {
        return new FindProfileOutput(
                user.getProfilePictureKey(),
                user.getProfilePicture().getPreSignedUrl(),
                user.getProfilePicture().getExpiration());
    }
}
