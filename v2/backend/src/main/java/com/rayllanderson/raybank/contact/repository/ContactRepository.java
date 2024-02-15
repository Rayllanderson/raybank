package com.rayllanderson.raybank.contact.repository;

import com.rayllanderson.raybank.contact.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, String> {
    List<Contact> findAllByOwnerId(String ownerId);
    boolean existsContactByAccount_IdAndOwnerId(String accountId, String ownerId);
    Optional<Contact> findByAccount_Id(String accountId);
    boolean existsContactByIdAndOwnerId(String contactId, String ownerId);
}
