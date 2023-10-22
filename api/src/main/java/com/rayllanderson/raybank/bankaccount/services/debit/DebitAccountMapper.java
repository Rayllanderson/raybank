package com.rayllanderson.raybank.bankaccount.services.debit;

import com.rayllanderson.raybank.bankaccount.facades.debit.DebitAccountFacadeInput;
import com.rayllanderson.raybank.bankaccount.services.transfer.BankAccountTransferInput;
import com.rayllanderson.raybank.shared.dtos.Type;
import com.rayllanderson.raybank.transaction.models.TransactionMethod;
import com.rayllanderson.raybank.transaction.models.TransactionType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {TransactionType.class, TransactionMethod.class, Type.class})
public interface DebitAccountMapper {
    DebitAccountInput from(DebitAccountFacadeInput facadeInput);

    @Mapping(target = "accountId", source = "senderId")
    @Mapping(target = "transaction.transactionType", expression = "java(TransactionType.TRANSFER)")
    @Mapping(target = "transaction.transactionMethod", expression = "java(TransactionMethod.ACCOUNT_TRANSFER)")
    @Mapping(target = "destination.identifier", source = "beneficiaryAccountNumber")
    @Mapping(target = "destination.type", expression = "java(Type.ACCOUNT)")
    DebitAccountInput from(BankAccountTransferInput input);
}
