package com.rayllanderson.raybank.users.gateway;

import com.rayllanderson.raybank.core.exceptions.NotFoundException;
import com.rayllanderson.raybank.users.model.User;
import com.rayllanderson.raybank.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.USER_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class UserPostgresGateway implements UserGateway {

    private final UserRepository userRepository;

    @Override
    public User findById(final String id) {
        return userRepository.findById(id).orElseThrow(() -> NotFoundException.withFormatted(USER_NOT_FOUND, "Usuário não encontrado"));
    }
}
