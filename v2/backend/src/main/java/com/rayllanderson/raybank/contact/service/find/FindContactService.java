package com.rayllanderson.raybank.contact.service.find;

import com.rayllanderson.raybank.contact.gateway.ContactGateway;
import com.rayllanderson.raybank.contact.model.Contact;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindContactService {

    private final FindContactMapper mapper;
    private final ContactGateway contactGateway;

    public ContactDetailsOutput findById(final String id) {
        final Contact contact = contactGateway.findById(id);
        return mapper.from(contact);
    }

    public List<ContactListOutput> findAllByOwnerId(final String ownerId) {
        final List<Contact> contacts = contactGateway.findAllByOnwerId(ownerId);
        return mapper.from(contacts);
    }
}
