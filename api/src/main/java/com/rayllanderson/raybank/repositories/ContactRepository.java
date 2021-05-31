package com.rayllanderson.raybank.repositories;

import com.rayllanderson.raybank.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
