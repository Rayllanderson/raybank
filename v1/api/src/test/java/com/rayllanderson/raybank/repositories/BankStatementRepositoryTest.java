package com.rayllanderson.raybank.repositories;

import com.rayllanderson.raybank.models.BankAccount;
import com.rayllanderson.raybank.models.BankStatement;
import com.rayllanderson.raybank.models.User;
import com.rayllanderson.raybank.models.enums.StatementType;
import com.rayllanderson.raybank.utils.BankAccountCreator;
import com.rayllanderson.raybank.utils.UserCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BankStatementRepositoryTest {

    @Autowired
    private BankStatementRepository statementRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Test
    void findAllByAccountOwnerIdAndStatementType() {
        User user = this.createUserSavedWithBankAccountSaved();
        BankAccount account = user.getBankAccount();
        Assertions.assertThat(user.getId()).isNotNull();

        BankStatement statement = statementRepository.save(BankStatement.createDepositStatement(
                new BigDecimal(150), account
        ));
        BankStatement secondStatement = statementRepository.save(BankStatement.createBoletoPaymentStatement(
                new BigDecimal(300), account
        ));

        account.getStatements().addAll(Arrays.asList(statement, secondStatement));
        bankAccountRepository.save(account);

        Assertions.assertThat(statementRepository.findAllByAccountOwnerId(account.getId()).size()).isEqualTo(2);

        Assertions.assertThat(statementRepository.findAllByAccountOwnerIdAndStatementType(
                account.getId(), StatementType.DEPOSIT).size()).isEqualTo(1);

        Assertions.assertThat(statementRepository.findAllByAccountOwnerIdAndStatementType(
                account.getId(), StatementType.BRAZILIAN_BOLETO).size()).isEqualTo(1);
    }

    @Test
    void findAllByAccountOwnerIdAndStatementTypeNot() {
        User user = this.createUserSavedWithBankAccountSaved();
        BankAccount account = user.getBankAccount();
        Assertions.assertThat(user.getId()).isNotNull();

        BankStatement statement = statementRepository.save(BankStatement.createDepositStatement(
                new BigDecimal(150), account
        ));

        account.getStatements().add(statement);
        bankAccountRepository.save(account);

        Assertions.assertThat(statementRepository.findAllByAccountOwnerId(account.getId()).size()).isEqualTo(1);

        Assertions.assertThat(statementRepository.findAllByAccountOwnerIdAndStatementTypeNot(
                account.getId(), StatementType.DEPOSIT).size()).isEqualTo(0);

        Assertions.assertThat(statementRepository.findAllByAccountOwnerIdAndStatementTypeNot(
                account.getId(), StatementType.TRANSFER).size()).isEqualTo(1);

    }

    public User createUserSavedWithBankAccountSaved(){
        User user = userRepository.save(UserCreator.createUserToBeSaved());
        BankAccount account = BankAccountCreator.createBankAccountToBeSavedWithoutCreditCardAndWithoutUser();
        account.setUser(user);
        account = bankAccountRepository.save(account);
        user.setBankAccount(account);
       return userRepository.save(user);
    }
}