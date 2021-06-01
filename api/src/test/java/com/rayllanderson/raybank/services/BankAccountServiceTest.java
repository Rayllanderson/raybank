package com.rayllanderson.raybank.services;

import com.rayllanderson.raybank.dtos.requests.bank.BankTransferDto;
import com.rayllanderson.raybank.models.BankAccount;
import com.rayllanderson.raybank.models.BankStatement;
import com.rayllanderson.raybank.repositories.BankAccountRepository;
import com.rayllanderson.raybank.repositories.BankStatementRepository;
import com.rayllanderson.raybank.utils.BankAccountCreator;
import com.rayllanderson.raybank.utils.BankTransferCreator;
import com.rayllanderson.raybank.utils.StatementCreator;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class BankAccountServiceTest {

    @InjectMocks
    private BankAccountService bankAccountService;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private BankStatementRepository statementRepository;

    @Mock
    private UserFinderService userFinderService;

    @BeforeEach
    void setUp() {
        BDDMockito.when(bankAccountRepository.save(ArgumentMatchers.any(BankAccount.class)))
                .thenReturn(BankAccountCreator.createBankAccountSaved());
        BDDMockito.when(statementRepository.save(ArgumentMatchers.any(BankStatement.class)))
                .thenReturn(StatementCreator.createTransferStatement());
        BDDMockito.when(userFinderService.findByPixOrAccountNumber(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt()))
                .thenReturn(UserCreator.createUserSavedWithAccount());
    }

    @Test
    void transfer() {
        BankTransferDto transaction = BankTransferCreator.createBankTransferDto();
        Assertions.assertThatCode(() -> bankAccountService.transfer(transaction)).doesNotThrowAnyException();
    }
}