package com.rayllanderson.raybank.users.services.profile;

import com.rayllanderson.raybank.core.exceptions.NotFoundException;
import com.rayllanderson.raybank.users.gateway.UserGateway;
import com.rayllanderson.raybank.users.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.PROFILE_IMAGE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class FindProfileService {

    private final UserGateway userGateway;

    public Optional<FindProfileOutput> findByUserId(String userId) {
        final User user = userGateway.findById(userId);

        if (user.doesNotHaveProfilePicture()) {
            throw NotFoundException.with(PROFILE_IMAGE_NOT_FOUND);
        }

        if (user.getProfilePicture().isExpired()) {
            return Optional.empty();
        }

        return Optional.of(FindProfileOutput.from(user));
    }
}
