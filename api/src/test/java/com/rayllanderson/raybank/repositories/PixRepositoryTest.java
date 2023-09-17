package com.rayllanderson.raybank.repositories;

import com.rayllanderson.raybank.pix.model.Pix;
import com.rayllanderson.raybank.pix.repository.PixRepository;
import com.rayllanderson.raybank.users.model.User;
import com.rayllanderson.raybank.users.repository.UserRepository;
import com.rayllanderson.raybank.utils.PixCreator;
import com.rayllanderson.raybank.utils.UserCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
class PixRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PixRepository pixRepository;

    @BeforeEach
    void setUp(){
        userRepository.deleteAll();
        pixRepository.deleteAll();
    }

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

    @Test
    void existsByKeyAndOwnerId_ReturnTrue_WhenUserHasKey() {
        User userToBeSaved = userRepository.save(UserCreator.createUserToBeSavedWithCollectionsNonNull());
        Pix pixToBeSaved = PixCreator.createPixToBeSavedWithoutUser();
        pixToBeSaved.setOwner(userToBeSaved);
        pixRepository.save(pixToBeSaved);

        String key = pixToBeSaved.getKey();
        var ownerId =  userToBeSaved.getId();
        Assertions.assertThat(pixRepository.existsByKeyAndOwnerId(key, ownerId)).isTrue();
    }

    @Test
    void existsByKeyAndOwnerId_ReturnFalse_WhenKeyIsNotFromHis() {
        User userToBeSaved = userRepository.save(UserCreator.createUserToBeSavedWithCollectionsNonNull());
        User userWithoutPix = userRepository.save(UserCreator.createUserToBeSaved());
        Pix pixToBeSaved = PixCreator.createPixToBeSavedWithoutUser();
        pixToBeSaved.setOwner(userToBeSaved);
        pixRepository.save(pixToBeSaved);

        String key = pixToBeSaved.getKey();
        var ownerId =  userToBeSaved.getId();
        var notTheOwnerId = userWithoutPix.getId();
        Assertions.assertThat(pixRepository.existsByKeyAndOwnerId(key, ownerId)).isTrue();
        Assertions.assertThat(pixRepository.existsByKeyAndOwnerId(key, notTheOwnerId)).isFalse();
    }

    @Test
    void existsByKeyAndOwnerId_ReturnFalse_WhenUserHasNoKey() {
        User userToBeSaved = userRepository.save(UserCreator.createUserToBeSavedWithCollectionsNonNull());

        String key = "any key here or anything else. it doesn't exists anyway";
        var ownerId =  userToBeSaved.getId();
        Assertions.assertThat(pixRepository.existsByKeyAndOwnerId(key, ownerId)).isFalse();
    }

    @Test
    void deleteByIdAndOwnerId_RemovePix_WhenSuccessful() {
        User userToBeSaved = userRepository.save(UserCreator.createUserToBeSavedWithCollectionsNonNull());
        Pix pixToBeSaved = PixCreator.createPixToBeSavedWithoutUser();
        pixToBeSaved.setOwner(userToBeSaved);
        pixRepository.save(pixToBeSaved);

        String key = pixToBeSaved.getKey();
        var ownerId =  userToBeSaved.getId();
        //garantindo que existe antes de deletar
        Assertions.assertThat(pixRepository.existsByKeyAndOwnerId(key, ownerId)).isTrue();

        //deletando
        pixRepository.deleteByIdAndOwnerId(pixToBeSaved.getId(), ownerId);
        //garantindo que realmente foi apagada
        Assertions.assertThat(pixRepository.existsByKeyAndOwnerId(key, ownerId)).isFalse();
    }

    @Test
    void deleteByIdAndOwnerId_DoNotRemove_WhenUserIsNotTheOwner() {
        User owner = userRepository.save(UserCreator.createUserToBeSavedWithCollectionsNonNull());
        User notTheOwner = userRepository.save(UserCreator.createUserToBeSaved());
        Pix pixToBeSaved = PixCreator.createPixToBeSavedWithoutUser();
        pixToBeSaved.setOwner(owner);
        pixRepository.save(pixToBeSaved);

        String key = pixToBeSaved.getKey();
        var notTheOwnerId = notTheOwner.getId();

        // tentando deletar uma chave que não é dele
        pixRepository.deleteByIdAndOwnerId(pixToBeSaved.getId(), notTheOwnerId);

        //garantindo que não foi deletado
        Assertions.assertThat(pixRepository.existsByKeyAndOwnerId(key, owner.getId())).isTrue();
    }

}