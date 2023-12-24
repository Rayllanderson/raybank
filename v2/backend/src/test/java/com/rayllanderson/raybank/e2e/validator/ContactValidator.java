package com.rayllanderson.raybank.e2e.validator;

import com.rayllanderson.raybank.contact.model.Contact;
import com.rayllanderson.raybank.contact.repository.ContactRepository;
import org.assertj.core.api.ListAssert;

import static org.assertj.core.api.Assertions.assertThat;

public interface ContactValidator {

    default ListAssert<Contact> assertThatContactsFromAccount(String accountId) {
        final var allByAccountId = getContactRepository().findAllByOwnerId(accountId);
        return assertThat(allByAccountId);
    }

    ContactRepository getContactRepository();
}
