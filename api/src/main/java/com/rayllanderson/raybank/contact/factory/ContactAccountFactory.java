package com.rayllanderson.raybank.contact.factory;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.transaction.models.TransactionMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ContactAccountFactory {
    private final List<ContactAccountFinder> accountFinders;

    public BankAccount find(String id, TransactionMethod method) {
        return accountFinders.stream()
                .filter(f -> f.supports(method))
                .findFirst()
                .orElseThrow()
                .find(id);
    }

}
