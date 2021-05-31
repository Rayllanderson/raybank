package com.rayllanderson.raybank.repositories;

import com.rayllanderson.raybank.models.Pix;
import com.rayllanderson.raybank.models.User;
import com.rayllanderson.raybank.utils.PixCreator;
import com.rayllanderson.raybank.utils.UserCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
class PixRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PixRepository pixRepository;

    @Test
    void countUserPixKeysById_ReturnNumberOfPixKeys_WhenUserHasKey() {
        User userToBeSaved = userRepository.save(UserCreator.createUserToBeSavedWithCollectionsNonNull());
        Pix pixToBeSaved = PixCreator.createPixToBeSavedWithoutUser();
        pixToBeSaved.setOwner(userToBeSaved);
        pixRepository.save(pixToBeSaved);
        Pix anotherPixToBeSaved = PixCreator.createAnotherPixToBeSavedWithoutUser();
        anotherPixToBeSaved.setOwner(userToBeSaved);
        pixRepository.save(anotherPixToBeSaved);

        Integer expectedNumberOfPixKey = 2;

        Assertions.assertThat(userToBeSaved).isNotNull();
        Assertions.assertThat(pixRepository.countByOwnerId(userToBeSaved.getId())).isEqualTo(expectedNumberOfPixKey);
    }

    @Test
    void countUserPixKeysById_ReturnZero_WhenUserHasNoKey() {
        User userToBeSaved = userRepository.save(UserCreator.createUserToBeSavedWithCollectionsNonNull());

        Integer expectedNumberOfPixKey = 0;

        Assertions.assertThat(userToBeSaved).isNotNull();
        Assertions.assertThat(pixRepository.countByOwnerId(userToBeSaved.getId())).isEqualTo(expectedNumberOfPixKey);
    }

}