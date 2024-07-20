package com.rayllanderson.raybank.users.services.profile;

import com.rayllanderson.raybank.core.services.S3Service;
import com.rayllanderson.raybank.users.gateway.UserGateway;
import com.rayllanderson.raybank.users.model.ProfilePicture;
import com.rayllanderson.raybank.users.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteProfilePictureService {

    private final UserGateway userGateway;
    private final S3Service s3Service;

    public void delete(final String userId) {
        final User user = userGateway.findById(userId);

        if (user.doesNotHaveProfilePicture()) {
            return;
        }

        deleteProfilePicture(user.getProfilePicture());

        user.deleteProfilePicture();
        userGateway.save(user);
    }

    private void deleteProfilePicture(final ProfilePicture profilePicture) {
        s3Service.deleteFile(profilePicture.getKey());
        if (profilePicture.getThumbnailKey() != null) {
            s3Service.deleteFile(profilePicture.getThumbnailKey());
        }
    }
}
