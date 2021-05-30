package com.rayllanderson.raybank.services;

import com.rayllanderson.raybank.dtos.requests.user.UserPostDto;
import com.rayllanderson.raybank.dtos.responses.UserPostResponseDto;
import com.rayllanderson.raybank.exceptions.BadRequestException;
import com.rayllanderson.raybank.models.User;
import com.rayllanderson.raybank.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserPostResponseDto register (UserPostDto userDto){
        this.assertThatUsernameNotExists(userDto.getUsername());
        User userToBeSaved =  userDto.toUser();
        return UserPostResponseDto.fromUser(userRepository.save(userToBeSaved));
    }

    /**
     * Verifica no banco de dados se o username passado já está registrado no banco de dados
     * @param username username a ser verificado
     * @throws BadRequestException se o username já estiver cadastrado
     */
    public void assertThatUsernameNotExists(String username) throws BadRequestException{
        boolean usernameExists = userRepository.existsByUsername(username);
        if (usernameExists) {
            throw new BadRequestException("Username já está em uso. Tente outro.");
        }
    }
}
