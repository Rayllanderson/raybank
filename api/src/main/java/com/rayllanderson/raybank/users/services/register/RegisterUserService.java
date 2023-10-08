package com.rayllanderson.raybank.users.services.register;

import com.rayllanderson.raybank.bankaccount.services.BankAccountService;
import com.rayllanderson.raybank.core.exceptions.BadRequestException;
import com.rayllanderson.raybank.core.security.keycloak.KeycloakProvider;
import com.rayllanderson.raybank.users.constants.Groups;
import com.rayllanderson.raybank.users.model.User;
import com.rayllanderson.raybank.users.model.UserType;
import com.rayllanderson.raybank.users.repository.UserRepository;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.rayllanderson.raybank.users.services.register.RegisterUtils.getGroupsByRegisterType;
import static com.rayllanderson.raybank.users.services.register.RegisterUtils.getIdFromHeader;

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
            tryToSaveOnDatabase(user, userId);
            return new RegisterUserOutput(userId);
        } else {
            throw handlerError(user, response);
        }
    }

    private void tryToSaveOnDatabase(RegisterUserInput user, String userId) {
        try {
            saveOnDatabase(user, userId);
        } catch (final Exception e) {
            log.error("Failed to save user {} on database. Unregistering from provider", userId);
            unregisterFromKeycloak(userId);
            throw e;
        }
    }

    private void unregisterFromKeycloak(final String userId) {
        keycloakProvider.getRealmInstance().users().delete(userId);
    }

    private void saveOnDatabase(final RegisterUserInput userInput, final String userId) {
        final String registerType = userInput.getRegisterType().name();
        User userToBeSaved = userInput.toModel();
        userToBeSaved.setId(userId);
        userToBeSaved.setAuthorities(Groups.valueOf(registerType).name());
        userToBeSaved.setType(UserType.valueOf(registerType));

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
        final var groups = getGroupsByRegisterType(user.getRegisterType());
        keycloakUser.setGroups(groups);

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

    private static CredentialRepresentation createPasswordCredentials(final String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }
}
