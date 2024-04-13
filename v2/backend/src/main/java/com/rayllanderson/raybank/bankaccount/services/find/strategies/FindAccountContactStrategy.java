package com.rayllanderson.raybank.bankaccount.services.find.strategies;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.bankaccount.services.find.FindAccountType;
import com.rayllanderson.raybank.contact.gateway.ContactGateway;
import com.rayllanderson.raybank.users.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindAccountContactStrategy implements FindAccountStrategy {
    private final ContactGateway contactGateway;

    @Override
    public BankAccount find(String value) {
        final var contact = contactGateway.findById(value);
        return new BankAccount(contact.getAccount().getId(), Integer.valueOf(contact.getAccount().getNumber()), null, null, null, null, null, new User(null, contact.getName(), null, null));
    }

    @Override
    public boolean supports(FindAccountType type) {
        return FindAccountType.CONTACT.equals(type);
    }
}
