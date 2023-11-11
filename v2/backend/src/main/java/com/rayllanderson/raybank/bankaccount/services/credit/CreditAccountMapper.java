package com.rayllanderson.raybank.bankaccount.services.credit;

import com.rayllanderson.raybank.bankaccount.services.deposit.DepositAccountInput;
import com.rayllanderson.raybank.bankaccount.services.transfer.BankAccountTransferInput;
import com.rayllanderson.raybank.shared.dtos.Type;
import com.rayllanderson.raybank.transaction.models.TransactionMethod;
import com.rayllanderson.raybank.transaction.models.TransactionType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {TransactionType.class, TransactionMethod.class, Type.class})
public interface CreditAccountMapper {

    @Mapping(target = "number", source = "input.beneficiaryAccountNumber")
    @Mapping(target = "origin.identifier", source = "input.senderId")
    @Mapping(target = "origin.type", expression = "java(Type.ACCOUNT)")
    @Mapping(target = "origin.referenceTransactionId", source = "referenceTransactionId")
    @Mapping(target = "transactionType", expression = "java(TransactionType.TRANSFER)")
    @Mapping(target = "transactionMethod", expression = "java(TransactionMethod.ACCOUNT_TRANSFER)")
    CreditAccountByNumberInput from(BankAccountTransferInput input, String referenceTransactionId);

    @Mapping(target = "origin.identifier", source = "input.accountId")
    @Mapping(target = "origin.type", expression = "java(Type.ACCOUNT)")
    @Mapping(target = "origin.referenceTransactionId", source = "referenceTransactionId")
    @Mapping(target = "transactionType", expression = "java(TransactionType.DEPOSIT)")
    @Mapping(target = "transactionMethod", expression = "java(TransactionMethod.ACCOUNT)")
    CreditAccountInput from(DepositAccountInput input, String referenceTransactionId);

    CreditAccountInput from(CreditAccountByNumberInput input, String accountId);

}
