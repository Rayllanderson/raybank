package com.rayllanderson.raybank.bankaccount.services.transfer;

import com.rayllanderson.raybank.bankaccount.controllers.transfer.BankAccountTransferRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankAccountTransferMapper {
    BankAccountTransferInput from(BankAccountTransferRequest request, String senderId);
}
