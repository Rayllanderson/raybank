package com.rayllanderson.raybank.users.services.profile;

import com.rayllanderson.raybank.core.services.S3PresignUrlOutput;
import com.rayllanderson.raybank.users.model.User;

import java.time.Instant;

public record ProfilePictureOutput(
        OriginalProfileOutput originalImage,
        ThumbnailProfileOutput thumbnail
) {

    record OriginalProfileOutput(String preSignedUrl, Instant expiration) {
        public static OriginalProfileOutput from(User user) {
            return new OriginalProfileOutput(
                    user.getProfilePicture().getPreSignedUrl(),
                    user.getProfilePicture().getExpiration());
        }

        public static OriginalProfileOutput from(S3PresignUrlOutput s3PresignUrlOutput) {
            return new OriginalProfileOutput(s3PresignUrlOutput.url().toString(), s3PresignUrlOutput.expiration());
        }
    }

    record ThumbnailProfileOutput(String preSignedUrl, Instant expiration) {
        public static ThumbnailProfileOutput from(User user) {
            return new ThumbnailProfileOutput(
                    user.getProfilePicture().getThumbnailPreSignedUrl(),
                    user.getProfilePicture().getExpiration());
        }

        public static ThumbnailProfileOutput from(S3PresignUrlOutput s3PresignUrlOutput) {
            return new ThumbnailProfileOutput(s3PresignUrlOutput.url().toString(), s3PresignUrlOutput.expiration());
        }
    }

    public static ProfilePictureOutput from(User user) {
        return new ProfilePictureOutput(OriginalProfileOutput.from(user), ThumbnailProfileOutput.from(user));
    }

    public static ProfilePictureOutput from(S3PresignUrlOutput s3PresignUrlOutput, S3PresignUrlOutput s3ThumbPresignUrlOutput) {
        return new ProfilePictureOutput(OriginalProfileOutput.from(s3PresignUrlOutput), ThumbnailProfileOutput.from(s3ThumbPresignUrlOutput));
    }
}
