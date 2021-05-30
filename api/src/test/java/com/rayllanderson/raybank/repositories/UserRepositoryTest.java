package com.rayllanderson.raybank.repositories;

import com.rayllanderson.raybank.models.User;
import com.rayllanderson.raybank.utils.UserCreator;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@Log4j2
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void save_PersistUser_WhenSuccessful() {
        User userToBeSaved = UserCreator.createUserToBeSaved();
        User userSaved = userRepository.save(userToBeSaved);

        Assertions.assertThat(userSaved).isNotNull();
        Assertions.assertThat(userSaved.getId()).isNotNull();
        Assertions.assertThat(userSaved.getName()).isEqualTo(userToBeSaved.getName());

        log.info(userToBeSaved);
    }
}