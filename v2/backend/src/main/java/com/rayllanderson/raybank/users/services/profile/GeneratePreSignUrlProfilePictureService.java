package com.rayllanderson.raybank.users.services.profile;

import com.rayllanderson.raybank.core.exceptions.NotFoundException;
import com.rayllanderson.raybank.core.services.S3PresignUrlOutput;
import com.rayllanderson.raybank.core.services.S3Service;
import com.rayllanderson.raybank.users.gateway.UserGateway;
import com.rayllanderson.raybank.users.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.PROFILE_IMAGE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class GeneratePreSignUrlProfilePictureService {

    private final S3Service s3Service;
    private final UserGateway userGateway;

    public S3PresignUrlOutput generate(String userId) {
        final User user = userGateway.findById(userId);

        if (user.doesNotHaveProfilePicture()) {
            throw NotFoundException.with(PROFILE_IMAGE_NOT_FOUND);
        }

        final S3PresignUrlOutput s3PresignUrlOutput = s3Service.generatePresignedUrl(user.getProfilePictureKey());

        user.updateProfilePicturePreSignedUrl(s3PresignUrlOutput.url().toString(), s3PresignUrlOutput.expiration());
        userGateway.save(user);

        return s3PresignUrlOutput;
    }
}
