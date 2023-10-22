package com.rayllanderson.raybank.bankaccount.services.create;

import com.rayllanderson.raybank.bankaccount.gateway.BankAccountGateway;
import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.users.gateway.UserGateway;
import com.rayllanderson.raybank.users.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateBankAccountService {

    private final UserGateway userGateway;
    private final BankAccountGateway accountGateway;
    private final BankAccountNumberGenerator accountNumberGenerator;

    @Transactional
    public CreateBankAccountOutput create(CreateBankAccountInput input) {
        final User user = userGateway.findById(input.getUserId());

        final var accountNumber = accountNumberGenerator.generate();
        final BankAccount bankAccountToBeSaved = BankAccount.createFromUserType(accountNumber, user.getType(), user.getId());

        accountGateway.save(bankAccountToBeSaved);

        return CreateBankAccountOutput.from(bankAccountToBeSaved);
    }
}
