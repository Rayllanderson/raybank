package com.rayllanderson.raybank.repositories;

import com.rayllanderson.raybank.models.BankAccount;
import com.rayllanderson.raybank.models.Pix;
import com.rayllanderson.raybank.models.User;
import com.rayllanderson.raybank.services.UserService;
import com.rayllanderson.raybank.utils.BankAccountCreator;
import com.rayllanderson.raybank.utils.UserCreator;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@Log4j2
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private PixRepository pixRepository;

    @BeforeEach
    void setUp(){
        userRepository.deleteAll();
        bankAccountRepository.deleteAll();
        log.info("executou");
    }

    @Test
    void save_PersistUser_WhenSuccessful() {
        User userToBeSaved = UserCreator.createUserToBeSaved();
        User userSaved = userRepository.save(userToBeSaved);

        Assertions.assertThat(userSaved).isNotNull();
        Assertions.assertThat(userSaved.getId()).isNotNull();
        Assertions.assertThat(userSaved.getName()).isEqualTo(userToBeSaved.getName());

        log.info(userToBeSaved);
    }

    @Test
    void existsByPixKeysOrBankAccountNumber_ReturnTrue_WhenValueExists() {
        User userToBeSaved = userRepository.save(UserCreator.createUserToBeSaved());
        BankAccount bankAccount = bankAccountRepository.save(BankAccountCreator.createBankAccountToBeSavedWithoutCreditCardAndWithoutUser());
        userToBeSaved.setBankAccount(bankAccount);
        User userSaved = userRepository.save(userToBeSaved);

        Integer expectedBankAccountNumber = bankAccount.getAccountNumber();
        String expectedBankAccountNumberAsPixKey = expectedBankAccountNumber.toString();

        Assertions.assertThat(userSaved).isNotNull();
        Assertions.assertThat(userRepository.existsByPixKeysKeyOrBankAccountAccountNumber(expectedBankAccountNumberAsPixKey, expectedBankAccountNumber))
                .isTrue();

        log.info(userToBeSaved);
    }

    @Test
    void existsByPixKeysOrBankAccountNumber_ReturnFalse_WhenValueNotExists() {
        Assertions.assertThat(userRepository
                .existsByPixKeysKeyOrBankAccountAccountNumber("99995", 5466121))
                .isFalse();
    }

    @Test
    void findByPixKeysOrBankAccountNumber_ReturnUser_WhenValueExists() {
        User userToBeSaved = userRepository.save(UserCreator.createUserToBeSaved());
        BankAccount bankAccount = BankAccountCreator.createBankAccountToBeSavedWithoutCreditCardAndWithoutUser();
        bankAccount.setUser(userToBeSaved);
        bankAccount = bankAccountRepository.save(bankAccount);
        userToBeSaved.setBankAccount(bankAccount);
        User userSaved = userRepository.save(userToBeSaved);

        Integer expectedBankAccountNumber = bankAccount.getAccountNumber();
        String expectedBankAccountNumberAsPixKey = expectedBankAccountNumber.toString();

        Optional<User> userToBeFound = userRepository
                .findByPixKeysKeyOrBankAccountAccountNumber(expectedBankAccountNumberAsPixKey, expectedBankAccountNumber);

        Assertions.assertThat(userToBeFound).isNotNull();
        Assertions.assertThat(userToBeFound).isPresent();
        Assertions.assertThat(userToBeFound.get()).isEqualTo(userSaved);
    }

    @Test
    void findByPixKeysOrBankAccountNumber_ReturnFalse_WhenValueNotExists() {
        Assertions.assertThat(userRepository
                .findByPixKeysKeyOrBankAccountAccountNumber("99995", 5466121))
                .isNotPresent();
    }

    @Test
    void findByPixKeysOrBankAccountNumber_ReturnPix_WhenPixExists() {
        User userToBeSaved = userRepository.save(UserCreator.createUserToBeSaved());
        BankAccount bankAccount = BankAccountCreator.createBankAccountToBeSavedWithoutCreditCardAndWithoutUser();
        bankAccount.setUser(userToBeSaved);
        bankAccount = bankAccountRepository.save(bankAccount);
        userToBeSaved.setBankAccount(bankAccount);
        Pix pix = new Pix(null, "rayllanderson", userToBeSaved);
        pixRepository.save(pix);
        userToBeSaved.getPixKeys().add(pix);

        userRepository.save(userToBeSaved);

        Assertions.assertThat(pix.getId()).isNotNull();

        Assertions.assertThat(userRepository
                .findByPixKeysKeyOrBankAccountAccountNumber(pix.getKey(), 5466121))
                .isPresent();
    }
}