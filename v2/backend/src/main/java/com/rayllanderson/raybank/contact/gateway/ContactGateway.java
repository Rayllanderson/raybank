package com.rayllanderson.raybank.contact.gateway;

import com.rayllanderson.raybank.contact.model.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactGateway {
    void save(Contact c);
    Contact findById(String id);
    Optional<Contact> findByAccountIdAndOwnerId(String accountId, String ownerId);
    List<Contact> findAllByOnwerId(String onwerId);
    boolean existsByContactAccountIdAndOwnerId(String accountId, String ownerId);
    boolean existsByContactIdAndOwnerId(String contactId, String ownerId);
}
