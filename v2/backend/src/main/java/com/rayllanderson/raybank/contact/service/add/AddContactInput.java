package com.rayllanderson.raybank.contact.service.add;

import com.rayllanderson.raybank.transaction.models.TransactionMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AddContactInput {
    private String onwerId;
    private String contactId;
    private TransactionMethod transactionMethod;
}
