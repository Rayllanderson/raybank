package com.rayllanderson.raybank.users.services.update;

import com.rayllanderson.raybank.users.gateway.UserGateway;
import com.rayllanderson.raybank.users.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateUserService {

    private final UserGateway userGateway;

    @Transactional
    public void update(final UpdateUserInput userInput) {
        User user = userGateway.findById(userInput.id());

        String updatedName = getUpdatedName(userInput, user);

        log.info("update userInput {}. from {} to {}", userInput.id(), user.getName(), updatedName);

        user.updateName(updatedName);
        userGateway.save(user);
    }

    private static String getUpdatedName(UpdateUserInput input, User userToBeUpdated) {
        String name = userToBeUpdated.getName();
        if (input.updatedFirstName() != null) {
            String lastName = name.split(" ")[1];
            name = input.updatedFirstName().concat(" ").concat(lastName);
        }

        if (input.updatedLastName() != null) {
            String firstName = name.split(" ")[0];
            name = firstName.concat(" ").concat(input.updatedLastName());
        }

        return name;
    }
}
