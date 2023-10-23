package com.rayllanderson.raybank.contact.gateway;

import com.rayllanderson.raybank.contact.model.Contact;

import java.util.List;

public interface ContactGateway {
    void save(Contact c);
    Contact findById(String id);
    List<Contact> findAllByOnwerId(String onwerId);
    boolean existsByContactAccountId(String accountId);
}
