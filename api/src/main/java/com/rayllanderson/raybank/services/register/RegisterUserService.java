package com.rayllanderson.raybank.services.register;

import com.rayllanderson.raybank.constants.Groups;
import com.rayllanderson.raybank.exceptions.BadRequestException;
import com.rayllanderson.raybank.models.User;
import com.rayllanderson.raybank.repositories.UserRepository;
import com.rayllanderson.raybank.security.keycloak.KeycloakProvider;
import com.rayllanderson.raybank.services.BankAccountService;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterUserService {

    private final UserRepository userRepository;
    private final BankAccountService bankAccountService;
    private final KeycloakProvider keycloakProvider;

    @Transactional
    public RegisterUserOutput register(final RegisterUserInput user) {
        this.assertThatUsernameNotExists(user.getUsername());

        final Response response = registerUserOnKeycloak(user);

        final boolean hasSuccessfullyRegistered = response.getStatus() == HttpStatus.CREATED.value();
        if (hasSuccessfullyRegistered) {
            final String userId = getIdFromHeader(response);
            saveOnDatabase(user, userId);
            return new RegisterUserOutput(userId);
        } else {
            throw handlerError(user, response);
        }
    }

    private void saveOnDatabase(final RegisterUserInput userInput, final String userId) {
        User userToBeSaved = userInput.toModel();
        userToBeSaved.setId(userId);
        userToBeSaved.setAuthorities(Groups.USER.name());

        userToBeSaved = userRepository.save(userToBeSaved);

        userToBeSaved.setBankAccount(bankAccountService.createAccountBank(userToBeSaved));
        userRepository.flush();
    }

    private Response registerUserOnKeycloak(final RegisterUserInput user) {
        final CredentialRepresentation credentialRepresentation = createPasswordCredentials(user.getPassword());

        UserRepresentation keycloakUser = new UserRepresentation();
        keycloakUser.setFirstName(user.getName());
        keycloakUser.setUsername(user.getUsername());
        keycloakUser.setCredentials(List.of(credentialRepresentation));
        keycloakUser.setEnabled(true);
        keycloakUser.setEmailVerified(false);
        keycloakUser.setGroups(List.of(Groups.USER.name()));

        return keycloakProvider.getRealmInstance().users().create(keycloakUser);
    }

    private static RuntimeException handlerError(RegisterUserInput user, Response response) {
        final boolean hasAlredyRegistered = response.getStatus() == HttpStatus.CONFLICT.value();
        if (hasAlredyRegistered) {
            log.warn("User '{}' is already registered in keycloak but not in local database", user.getUsername());
            return new BadRequestException("Username já está em uso. Tente outro.");
        }
        log.warn("Error to register user '{}' in keycloak. Response status = '{}'", user.getUsername(), response.getStatus());
        return new FailedToRegisterException("Erro ao registrar usuário. Tente mais tarde");
    }

    public void assertThatUsernameNotExists(final String username) throws BadRequestException {
        boolean usernameExists = userRepository.existsByUsername(username);
        if (usernameExists) {
            throw new BadRequestException("Username já está em uso. Tente outro.");
        }
    }

    private static String getIdFromHeader(final Response response) {
        return response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
    }

    private static CredentialRepresentation createPasswordCredentials(final String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }
}
