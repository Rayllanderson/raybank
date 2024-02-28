package com.rayllanderson.raybank.users.services.register;

import com.rayllanderson.raybank.bankaccount.services.create.CreateBankAccountService;
import com.rayllanderson.raybank.core.security.keycloak.KeycloakProvider;
import com.rayllanderson.raybank.users.constants.Groups;
import com.rayllanderson.raybank.users.model.TempUser;
import com.rayllanderson.raybank.users.model.User;
import com.rayllanderson.raybank.users.model.UserType;
import com.rayllanderson.raybank.users.repository.TempUserRepository;
import com.rayllanderson.raybank.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterUserService {

    private final UserRepository userRepository;
    private final TempUserRepository tempUserRepository;
    private final KeycloakProvider keycloakProvider;
    private final CreateBankAccountService createBankAccountService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public RegisterUserOutput register(final RegisterUserInput user) {
        tryToSaveOnDatabase(user);
        log.info("registered user {}-{}", user.getId(), user.getName());
        return new RegisterUserOutput(user.getId());
    }

    @Transactional
    public RegisterUserOutput preRegisterEstablishemnt(final RegisterUserInput user) {
        final var tempUser = new TempUser(user.getId());

        tempUserRepository.save(tempUser);
        eventPublisher.publishEvent(new TempUserCreatedEvent(tempUser));

        return new RegisterUserOutput(user.getId());
    }

    @Transactional
    public void registerEstablishment(final TempUserCreatedEvent event) {
        final var user = event.user();

        UserResource userResource = keycloakProvider.getRealmInstance().users().get(user.getId());
        List<GroupRepresentation> userGroups = userResource.groups();

        final boolean isEstablishment = userGroups.stream().anyMatch(g -> g.getName().equalsIgnoreCase(UserType.ESTABLISHMENT.name()));
        if (isEstablishment) {
            final UserRepresentation userRepresentation = userResource.toRepresentation();
            final RegisterUserInput userToBeSaved = RegisterUserInput.from(userRepresentation);
            tryToSaveOnDatabase(userToBeSaved);
            tempUserRepository.deleteById(user.getId());
            log.info("registered establisment {}-{}", user.getId(), userToBeSaved.getName());
        }
    }

    private void tryToSaveOnDatabase(RegisterUserInput user) {
        try {
            saveOnDatabase(user);
        } catch (final Exception e) {
            log.error("Failed to save user {} on database.", user.getId(), e);
            throw e;
        }
    }

    private void saveOnDatabase(final RegisterUserInput userInput) {
        final String registerType = userInput.getRegisterType().name();
        User userToBeSaved = userInput.toModel();
        userToBeSaved.setAuthorities(Groups.valueOf(registerType).name());
        userToBeSaved.setType(UserType.valueOf(registerType));

        userToBeSaved = userRepository.save(userToBeSaved);

        final var bankAccount = createBankAccountService.create(userToBeSaved.getId());
        userToBeSaved.addBankAccount(bankAccount.getId());

        userRepository.flush();
    }
}
