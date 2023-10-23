package com.rayllanderson.raybank.contact.repository;

import com.rayllanderson.raybank.contact.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, String> {
    List<Contact> findAllByOwnerId(String onwerId);
    boolean existsContactByAccount_Id(String accountId);
}
