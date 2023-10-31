package com.rayllanderson.raybank.e2e.factory;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.bankaccount.model.BankAccountStatus;
import com.rayllanderson.raybank.bankaccount.model.BankAccountType;
import com.rayllanderson.raybank.bankaccount.repository.BankAccountRepository;
import com.rayllanderson.raybank.users.model.User;
import com.rayllanderson.raybank.users.model.UserType;
import com.rayllanderson.raybank.users.repository.UserRepository;
import com.rayllanderson.raybank.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

import static com.rayllanderson.raybank.e2e.constants.Constants.ESTABLISHMENT_ID;

@Component
public class BankAccountCreator {

    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private UserRepository userRepository;

    public BankAccount newNormalBankAccount() {
        return newNormalBankAccount("kaguyaId", "kaguya", BigDecimal.ZERO);
    }

    public BankAccount newNormalBankAccount(String id) {
        return newNormalBankAccount(id, id, BigDecimal.ZERO);
    }

    public BankAccount newNormalBankAccountWithBalance(String id, BigDecimal balance) {
        return newNormalBankAccount(id, id, balance);
    }

    public BankAccount newNormalBankAccountWithBalance(String id, String balance) {
        return newNormalBankAccount(id, id, new BigDecimal(balance));
    }

    public BankAccount newNormalBankAccount(String id, String username, BigDecimal balance) {
        User user = user(id, username, UserType.USER);
        userRepository.save(user);
        BankAccount entity = BankAccount.create(Math.toIntExact(RandomUtils.generate(6)), BankAccountType.NORMAL, id);
        entity.setBalance(balance);
        final var bankAccount = bankAccountRepository.save(entity);
        user.addBankAccount(bankAccount.getId());
        userRepository.save(user);
        return bankAccount;
    }

    private static User user(String id, String username, UserType type) {
        return User.builder().id(id).bankAccountId(null).authorities(null).username(username).type(type).name(username).build();
    }

    public BankAccount newEstablishmentBankAccount() {
        return newEstablishmentBankAccount(ESTABLISHMENT_ID, BankAccountStatus.ACTIVE);
    }

    public BankAccount newEstablishmentBankAccount(String id, BankAccountStatus status) {
        User user = user(id, "amazon", UserType.ESTABLISHMENT);
        userRepository.save(user);
        final var bankAccount = bankAccountRepository.save(BankAccount.create(Math.toIntExact(RandomUtils.generate(6)), BankAccountType.ESTABLISHMENT, id));
        bankAccount.setStatus(status);
        bankAccountRepository.save(bankAccount);
        user.addBankAccount(bankAccount.getId());
        userRepository.save(user);
        return bankAccount;
    }
}
