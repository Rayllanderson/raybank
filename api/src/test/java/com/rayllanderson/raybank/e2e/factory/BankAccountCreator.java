package com.rayllanderson.raybank.e2e.factory;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.bankaccount.model.BankAccountStatus;
import com.rayllanderson.raybank.bankaccount.model.BankAccountType;
import com.rayllanderson.raybank.bankaccount.repository.BankAccountRepository;
import com.rayllanderson.raybank.users.model.User;
import com.rayllanderson.raybank.users.model.UserType;
import com.rayllanderson.raybank.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.rayllanderson.raybank.e2e.constants.Constants.ESTABLISHMENT_ID;

@Component
public class BankAccountCreator {

    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private UserRepository userRepository;

    public BankAccount newNormalBankAccount() {
        User user = user("kaguyaId", "kaguya", UserType.USER);
        userRepository.save(user);
        final var bankAccount = bankAccountRepository.save(BankAccount.create(123456, BankAccountType.NORMAL, "kaguyaId"));
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
        final var bankAccount = bankAccountRepository.save(BankAccount.create(678910, BankAccountType.ESTABLISHMENT, id));
        bankAccount.setStatus(status);
        bankAccountRepository.save(bankAccount);
        user.addBankAccount(bankAccount.getId());
        userRepository.save(user);
        return bankAccount;
    }
}
