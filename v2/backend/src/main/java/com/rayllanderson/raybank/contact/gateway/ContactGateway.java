package com.rayllanderson.raybank.contact.gateway;

import com.rayllanderson.raybank.contact.model.Contact;

import java.util.List;

public interface ContactGateway {
    void save(Contact c);
    Contact findById(String id);
    Contact findByAccountId(String accountId);
    List<Contact> findAllByOnwerId(String onwerId);
    boolean existsByContactAccountIdAndOwnerId(String accountId, String ownerId);
    boolean existsByContactIdAndOwnerId(String contactId, String ownerId);
}
