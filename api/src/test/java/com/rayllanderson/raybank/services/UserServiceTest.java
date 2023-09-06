package com.rayllanderson.raybank.services;

import com.rayllanderson.raybank.dtos.requests.user.UserPostDto;
import com.rayllanderson.raybank.dtos.responses.user.UserPostResponseDto;
import com.rayllanderson.raybank.models.User;
import com.rayllanderson.raybank.repositories.UserRepository;
import com.rayllanderson.raybank.services.creditcard.CreditCardService;
import com.rayllanderson.raybank.utils.BankAccountCreator;
import com.rayllanderson.raybank.utils.CreditCardCreator;
import com.rayllanderson.raybank.utils.UserCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;


    @Mock
    private UserFinderService userFinderService;

    @Mock
    private BankAccountService bankAccountService;

    @Mock
    private CreditCardService creditCardService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder encoder;

    @BeforeEach
    void setUp() {
        //save
        BDDMockito.when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(UserCreator.createUserWithId());
        //user finder - find
        BDDMockito.when(userFinderService.findById(ArgumentMatchers.anyLong())).thenReturn(UserCreator.createUserWithId());
        //find
        BDDMockito.when(userRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(UserCreator.createUserWithId()));
        //delete
        BDDMockito.doNothing().when(userRepository).deleteById(ArgumentMatchers.anyLong());
        //criando conta bancária
        BDDMockito.when(bankAccountService.createAccountBank(ArgumentMatchers.any(User.class)))
                .thenReturn(BankAccountCreator.createBankAccountSaved());
        //criando cartão de crédito
        BDDMockito.when(creditCardService.createCreditCard(ArgumentMatchers.any()))
                .thenReturn(CreditCardCreator.createCreditCardSaved());

        BDDMockito.when(encoder.encode(ArgumentMatchers.anyString())).thenReturn("$2a$10$vjDC.rpWSRb7eDwXuGtGaOhv0Bc.S598scA/tlU0Vo1ZYY3NV4lea");
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
        User userFound = userFinderService.findById(UserCreator.createUserWithId().getId());
        Assertions.assertThat(userFound).isNotNull();
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