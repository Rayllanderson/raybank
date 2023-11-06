package com.rayllanderson.raybank.bankaccount.services.find;

import com.rayllanderson.raybank.bankaccount.controllers.find.BankAccountDetailsResponse;
import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FindAccountMapper {

    @Mapping(target = "account", expression = "java(mapFrom(bankAccount))")
    BankAccountDetailsOutput from(BankAccount bankAccount);

    BankAccountDetailsOutput.AccountOutput mapFrom(BankAccount account);

    BankAccountDetailsResponse from(BankAccountDetailsOutput output);
}
