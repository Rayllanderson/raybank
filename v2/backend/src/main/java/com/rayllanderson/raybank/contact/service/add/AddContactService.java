package com.rayllanderson.raybank.contact.service.add;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.contact.factory.ContactAccountFactory;
import com.rayllanderson.raybank.contact.gateway.ContactGateway;
import com.rayllanderson.raybank.contact.model.Contact;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddContactService {

    private final ContactGateway contactGateway;
    private final ContactAccountFactory accountFactory;

    @Async
    @Transactional
    @Retryable(maxAttemptsExpression = "${retry.statement.create.maxAttempts}", backoff = @Backoff(maxDelayExpression = "${retry.statement.create.maxDelay}"))
    public void add(final AddContactInput input) {
        final BankAccount contactAccount = accountFactory.find(input.getContactId(), input.getTransactionMethod());

        final Optional<Contact> possibleExistingContact = contactGateway.findByAccountIdAndOwnerId(contactAccount.getId(), input.getOnwerId());
        possibleExistingContact.ifPresent(contact -> {
            if (contact.hasChangedName(contactAccount.getAccountName())) {
                contact.updateName(contactAccount.getAccountName());
            }
        });

        if (possibleExistingContact.isEmpty()) {
            createNewContact(input, contactAccount);
        }
    }

    private void createNewContact(AddContactInput input, BankAccount contactAccount) {
        final Contact contact = Contact.from(contactAccount, input.getOnwerId());
        contactGateway.save(contact);
    }
}
