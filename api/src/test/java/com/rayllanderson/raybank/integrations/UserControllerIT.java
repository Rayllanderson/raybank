package com.rayllanderson.raybank.integrations;

import com.rayllanderson.raybank.users.controllers.UserPostResponseDto;
import com.rayllanderson.raybank.users.controllers.find.UserResponseDto;
import com.rayllanderson.raybank.utils.UserCreator;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

@Log4j2
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerIT extends BaseApiTest {

    @Test
    void register_RegisterAnUser_WhenSuccessful() {
        var userToBeRegistered = UserCreator.createUserPostToBeSaved();

        ResponseEntity<UserPostResponseDto> userResponseEntity = rest.postForEntity("/api/v1/users",
                userToBeRegistered, UserPostResponseDto.class);

        Assertions.assertThat(userResponseEntity).isNotNull();
        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(userResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(userResponseEntity.getBody().getId()).isNotNull();
    }

    @Test
    void register_NonRegisterAnUser_WhenUsernameIsInvalid() {
        var userToBeRegistered = UserCreator.createUserPostToBeSaved();
        userToBeRegistered.setUsername("");

        ResponseEntity<UserPostResponseDto> userResponseEntity = rest.postForEntity("/api/v1/users",
                userToBeRegistered, UserPostResponseDto.class);

        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void register_NonRegisterAnUser_WhenUsernameIsLessThan3Character() {
        var userToBeRegistered = UserCreator.createUserPostToBeSaved();
        userToBeRegistered.setUsername("12");

        ResponseEntity<UserPostResponseDto> userResponseEntity = rest.postForEntity("/api/v1/users",
                userToBeRegistered, UserPostResponseDto.class);

        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void register_NonRegisterAnUser_WhenPasswordIsLessThan3Character() {
        var userToBeRegistered = UserCreator.createUserPostToBeSaved();
        userToBeRegistered.setPassword("12");

        ResponseEntity<UserPostResponseDto> userResponseEntity = rest.postForEntity("/api/v1/users",
                userToBeRegistered, UserPostResponseDto.class);

        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void find_ReturnsUserAuthData_WhenSuccessful() {
        ResponseEntity<UserResponseDto> userResponseEntity = super.get("/api/v1/users/authenticated", UserResponseDto.class);

        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(userResponseEntity.getBody()).isNotNull();
    }

    @Test
    void find_Returns403_WhenUserIsNotAuth() {
        //rest.getForEntity vai sem o header de autenticação, portanto, não autorizado
        ResponseEntity<UserResponseDto> userResponseEntity = rest.getForEntity("/api/v1/users/authenticated", UserResponseDto.class);
        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

}