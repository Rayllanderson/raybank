package com.rayllanderson.raybank.contact.gateway;

import com.rayllanderson.raybank.contact.repository.ContactRepository;
import com.rayllanderson.raybank.contact.model.Contact;
import com.rayllanderson.raybank.core.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

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
        return contactRepository.findById(id).orElseThrow(() -> NotFoundException.formatted("Contato não encontrado"));
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
