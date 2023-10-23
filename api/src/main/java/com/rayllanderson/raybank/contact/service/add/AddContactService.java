package com.rayllanderson.raybank.contact.service.add;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.contact.factory.ContactAccountFactory;
import com.rayllanderson.raybank.contact.gateway.ContactGateway;
import com.rayllanderson.raybank.contact.model.Contact;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddContactService {

    private final ContactGateway contactGateway;
    private final ContactAccountFactory accountFactory;

    @Async
    @Transactional
    public void add(final AddContactInput input) {
        final BankAccount bankAccount = accountFactory.find(input.getContactId(), input.getTransactionMethod());

        final boolean contactAlreadyExists = contactGateway.existsByContactAccountId(bankAccount.getId());
        if(contactAlreadyExists)
            return;

        final Contact contact = Contact.from(bankAccount, input.getOnwerId());
        contactGateway.save(contact);
    }
}
