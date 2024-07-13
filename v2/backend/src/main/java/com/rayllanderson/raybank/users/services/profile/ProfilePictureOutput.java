package com.rayllanderson.raybank.users.services.profile;

import com.rayllanderson.raybank.users.model.User;

import java.time.Instant;

public record FindProfileOutput(
        OriginalProfileOutput originalImage,
        ThumbnailProfileOutput thumbnail
) {

    record OriginalProfileOutput(String key, String preSignedUrl, Instant expiration) {
        public static OriginalProfileOutput from(User user) {
            return new OriginalProfileOutput(
                    user.getProfilePictureKey(),
                    user.getProfilePicture().getPreSignedUrl(),
                    user.getProfilePicture().getExpiration());
        }
    }

    record ThumbnailProfileOutput(String key, String preSignedUrl, Instant expiration) {
        public static ThumbnailProfileOutput from(User user) {
            return new ThumbnailProfileOutput(
                    user.getProfilePicture().getThumbnailKey(),
                    user.getProfilePicture().getThumbnailPreSignedUrl(),
                    user.getProfilePicture().getExpiration());
        }
    }

    public static FindProfileOutput from(User user) {
        return new FindProfileOutput(OriginalProfileOutput.from(user), ThumbnailProfileOutput.from(user));
    }
}
