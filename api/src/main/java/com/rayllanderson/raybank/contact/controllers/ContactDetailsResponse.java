package com.rayllanderson.raybank.contact.controllers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactDetailsResponse {
    private String id;
    private String name;
    private ContactAccountResponse account;

    @Getter
    @Setter
    public static class ContactAccountResponse {
        private String number;
    }
}
