package com.rayllanderson.raybank.contact.service.find;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactDetailsOutput {
    private String id;
    private String name;
    private ContactAccountOutput account;

    @Getter
    @Setter
    protected static class ContactAccountOutput {
        private String number;
    }
}
