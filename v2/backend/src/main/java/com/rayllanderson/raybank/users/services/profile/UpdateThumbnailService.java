package com.rayllanderson.raybank.users.services.profile;

import com.rayllanderson.raybank.core.services.S3PresignUrlOutput;
import com.rayllanderson.raybank.core.services.S3Service;
import com.rayllanderson.raybank.users.consumer.thumbnail.SqsMessage;
import com.rayllanderson.raybank.users.gateway.UserGateway;
import com.rayllanderson.raybank.users.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateThumbnailService {

    private final S3Service s3Service;
    private final UserGateway userGateway;

    public void updateThumbnail(final SqsMessage sqsMessage) {
        final User user = userGateway.findByImageKey(sqsMessage.keyWithoutPrefix());

        final S3PresignUrlOutput s3PresignUrlOutput = s3Service.generatePresignedUrl(sqsMessage.key());

        user.updateThumbnail(sqsMessage.key(), s3PresignUrlOutput.url().toString());
        userGateway.save(user);
    }
}
