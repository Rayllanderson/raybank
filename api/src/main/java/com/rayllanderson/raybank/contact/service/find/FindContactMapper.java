package com.rayllanderson.raybank.contact.service.find;

import com.rayllanderson.raybank.contact.controllers.ContactDetailsResponse;
import com.rayllanderson.raybank.contact.controllers.ContactListResponse;
import com.rayllanderson.raybank.contact.model.Contact;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FindContactMapper {
    ContactDetailsOutput from(Contact contact);
    ContactDetailsResponse from(ContactDetailsOutput contact);
    List<ContactListOutput> from(List<Contact> contacts);
    List<ContactListResponse> fromOutput(List<ContactListOutput> contacts);
}
