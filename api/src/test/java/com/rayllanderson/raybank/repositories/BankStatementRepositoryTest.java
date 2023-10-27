package com.rayllanderson.raybank.repositories;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.bankaccount.repository.BankAccountRepository;
import com.rayllanderson.raybank.statement.models.BankStatement;
import com.rayllanderson.raybank.statement.repositories.BankStatementRepository;
import com.rayllanderson.raybank.users.model.User;
import com.rayllanderson.raybank.users.repository.UserRepository;
import com.rayllanderson.raybank.utils.BankAccountCreator;
import com.rayllanderson.raybank.utils.UserCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

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

        BankStatement bankStatement = statementRepository.save(BankStatement.createDepositBankStatement(
                new BigDecimal(150), account
        ));
        BankStatement secondStatement = statementRepository.save(BankStatement.createBoletoPaymentBankStatement(
                new BigDecimal(300), account
        ));

//        account.getBankStatements().addAll(Arrays.asList(bankStatement, secondStatement));
        bankAccountRepository.save(account);

        Assertions.assertThat(statementRepository.findAllByAccountOwnerId(account.getId()).size()).isEqualTo(2);

        Assertions.assertThat(statementRepository.findAllByAccountOwnerIdAndType(
                account.getId(), BankStatementType.DEPOSIT).size()).isEqualTo(1);

        Assertions.assertThat(statementRepository.findAllByAccountOwnerIdAndType(
                account.getId(), BankStatementType.BRAZILIAN_BOLETO).size()).isEqualTo(1);
    }

    @Test
    void findAllByAccountOwnerIdAndStatementTypeNot() {
        User user = this.createUserSavedWithBankAccountSaved();
        BankAccount account = user.getBankAccount();
        Assertions.assertThat(user.getId()).isNotNull();

        BankStatement bankStatement = statementRepository.save(BankStatement.createDepositBankStatement(
                new BigDecimal(150), account
        ));

//        account.getBankStatements().add(bankStatement);
        bankAccountRepository.save(account);

        Assertions.assertThat(statementRepository.findAllByAccountOwnerId(account.getId()).size()).isEqualTo(1);

        Assertions.assertThat(statementRepository.findAllByAccountOwnerIdAndTypeNot(
                account.getId(), BankStatementType.DEPOSIT).size()).isEqualTo(0);

        Assertions.assertThat(statementRepository.findAllByAccountOwnerIdAndTypeNot(
                account.getId(), BankStatementType.TRANSFER).size()).isEqualTo(1);

    }

    public User createUserSavedWithBankAccountSaved(){
        User user = userRepository.save(UserCreator.createUserToBeSaved());
        BankAccount account = BankAccountCreator.createBankAccountToBeSavedWithoutCreditCardAndWithoutUser();
        account.attacthUser(user);
        account = bankAccountRepository.save(account);
        user.setBankAccount(account);
       return userRepository.save(user);
    }
}