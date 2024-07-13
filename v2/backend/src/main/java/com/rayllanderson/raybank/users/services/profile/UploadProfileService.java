package com.rayllanderson.raybank.users.services.profile;

import com.rayllanderson.raybank.core.services.S3Service;
import com.rayllanderson.raybank.core.services.S3UploadOutput;
import com.rayllanderson.raybank.users.gateway.UserGateway;
import com.rayllanderson.raybank.users.model.ProfilePicture;
import com.rayllanderson.raybank.users.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UploadProfileService {

    private final S3Service s3Service;
    private final UserGateway userGateway;

    public S3UploadOutput upload(String userId, MultipartFile file) throws IOException {
        final User user = userGateway.findById(userId);

        if (user.hasProfilePicture()) {
            deleteOldProfilePicture(user.getProfilePicture());
            user.getProfilePicture().reset();
        }

        final S3UploadOutput s3UploadOutput = s3Service.uploadFile(file);

        user.addProfilePicture(s3UploadOutput.toProfilePicture());
        userGateway.save(user);

        return s3UploadOutput;
    }


    private void deleteOldProfilePicture(final ProfilePicture oldProfilePictureKey) {
        s3Service.deleteFile(oldProfilePictureKey.getKey());
        s3Service.deleteFile(oldProfilePictureKey.getThumbnailKey());
    }

}
