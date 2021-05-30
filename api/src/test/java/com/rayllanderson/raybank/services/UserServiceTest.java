package com.rayllanderson.raybank.services;

import com.rayllanderson.raybank.dtos.requests.user.UserPostDto;
import com.rayllanderson.raybank.dtos.responses.UserPostResponseDto;
import com.rayllanderson.raybank.models.User;
import com.rayllanderson.raybank.repositories.UserRepository;
import com.rayllanderson.raybank.utils.UserCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        BDDMockito.when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(UserCreator.createUserWithId());
        BDDMockito.when(userRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(UserCreator.createUserWithId()));
        BDDMockito.doNothing().when(userRepository).deleteById(ArgumentMatchers.anyLong());
    }

    @Test
    void register_saveUser_whenSuccess() {
        UserPostDto userToBeSaved = UserCreator.createUserPostToBeSaved();
        Long expectedId = UserCreator.createUserWithId().getId();

        UserPostResponseDto savedUser = userService.register(userToBeSaved);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isNotNull();
        Assertions.assertThat(savedUser.getId()).isEqualTo(expectedId);
    }

    @Test
    void findById_ReturnsUser_WhenSuccessful() {
        User expectedUser = UserCreator.createUserWithId();
        User userFound = userService.findById(UserCreator.createUserWithId().getId());
        Assertions.assertThat(userFound).isNotNull();
        Assertions.assertThat(userFound).isEqualTo(expectedUser);
    }

    @Test
    void deleteById_DeleteUser_WhenSuccessful() {
        Assertions.assertThatCode(() -> userService.deleteById(1L)).doesNotThrowAnyException();
    }

    @Test
    void updateNameUsernameOrEmail_UpdateData_WhenSuccessful() {
        Assertions.assertThatCode(() -> userService.updateNameOrUsername(UserCreator.createUserPutDto()))
                .doesNotThrowAnyException();
    }

    @Test
    void updatePassword_UpdatePassword_WhenSuccessful() {
        Assertions.assertThatCode(() -> userService.updatePassword(UserCreator.createUserPutDto()))
                .doesNotThrowAnyException();
    }
}