package com.rayllanderson.raybank.repositories;

import com.rayllanderson.raybank.models.BankAccount;
import com.rayllanderson.raybank.models.Transaction;
import com.rayllanderson.raybank.models.User;
import com.rayllanderson.raybank.models.TransactionType;
import com.rayllanderson.raybank.utils.BankAccountCreator;
import com.rayllanderson.raybank.utils.UserCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Arrays;

@DataJpaTest
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository statementRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Test
    void findAllByAccountOwnerIdAndStatementType() {
        User user = this.createUserSavedWithBankAccountSaved();
        BankAccount account = user.getBankAccount();
        Assertions.assertThat(user.getId()).isNotNull();

        Transaction transaction = statementRepository.save(Transaction.createDepositTransaction(
                new BigDecimal(150), account
        ));
        Transaction secondStatement = statementRepository.save(Transaction.createBoletoPaymentTransaction(
                new BigDecimal(300), account
        ));

        account.getTransactions().addAll(Arrays.asList(transaction, secondStatement));
        bankAccountRepository.save(account);

        Assertions.assertThat(statementRepository.findAllByAccountOwnerId(account.getId()).size()).isEqualTo(2);

        Assertions.assertThat(statementRepository.findAllByAccountOwnerIdAndType(
                account.getId(), TransactionType.DEPOSIT).size()).isEqualTo(1);

        Assertions.assertThat(statementRepository.findAllByAccountOwnerIdAndType(
                account.getId(), TransactionType.BRAZILIAN_BOLETO).size()).isEqualTo(1);
    }

    @Test
    void findAllByAccountOwnerIdAndStatementTypeNot() {
        User user = this.createUserSavedWithBankAccountSaved();
        BankAccount account = user.getBankAccount();
        Assertions.assertThat(user.getId()).isNotNull();

        Transaction transaction = statementRepository.save(Transaction.createDepositTransaction(
                new BigDecimal(150), account
        ));

        account.getTransactions().add(transaction);
        bankAccountRepository.save(account);

        Assertions.assertThat(statementRepository.findAllByAccountOwnerId(account.getId()).size()).isEqualTo(1);

        Assertions.assertThat(statementRepository.findAllByAccountOwnerIdAndTypeNot(
                account.getId(), TransactionType.DEPOSIT).size()).isEqualTo(0);

        Assertions.assertThat(statementRepository.findAllByAccountOwnerIdAndTypeNot(
                account.getId(), TransactionType.TRANSFER).size()).isEqualTo(1);

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