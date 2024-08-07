package com.rayllanderson.raybank.users.gateway;

import com.rayllanderson.raybank.core.exceptions.NotFoundException;
import com.rayllanderson.raybank.users.model.User;
import com.rayllanderson.raybank.users.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.USER_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class UserPostgresGateway implements UserGateway {

    private final UserRepository userRepository;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findById(final String id) {
        return userRepository.findById(id).orElseThrow(() -> NotFoundException.withFormatted(USER_NOT_FOUND, "Usuário não encontrado"));
    }

    @Override
    @Transactional
    public User findByImageKey(String imageKey) {
        return this.userRepository.findByProfilePicture_Key(imageKey).orElseThrow(() -> NotFoundException.withFormatted(USER_NOT_FOUND, "Nenhum usuário encontrado com a imagem %s", imageKey));
    }
}
