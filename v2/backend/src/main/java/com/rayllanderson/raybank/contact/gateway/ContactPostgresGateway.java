package com.rayllanderson.raybank.contact.gateway;

import com.rayllanderson.raybank.contact.model.Contact;
import com.rayllanderson.raybank.contact.repository.ContactRepository;
import com.rayllanderson.raybank.core.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.CONTACT_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class ContactPostgresGateway implements ContactGateway {

    private final ContactRepository contactRepository;

    @Override
    public void save(Contact c) {
        contactRepository.save(c);
    }

    @Override
    public Contact findById(String id) {
        return contactRepository.findById(id).orElseThrow(() -> NotFoundException.withFormatted(CONTACT_NOT_FOUND, "Contato não encontrado"));
    }

    @Override
    public Optional<Contact> findByAccountIdAndOwnerId(String accountId, String ownerId) {
        return contactRepository.findByAccount_IdAndOwnerId(accountId, ownerId);
    }

    @Override
    public List<Contact> findAllByOnwerId(String onwerId) {
        return contactRepository.findAllByOwnerId(onwerId);
    }

    @Override
    public boolean existsByContactAccountIdAndOwnerId(String accountId, String ownerId) {
        return contactRepository.existsContactByAccount_IdAndOwnerId(accountId, ownerId);
    }

    @Override
    public boolean existsByContactIdAndOwnerId(String contactId, String ownerId) {
        return contactRepository.existsContactByIdAndOwnerId(contactId, ownerId);
    }
}
