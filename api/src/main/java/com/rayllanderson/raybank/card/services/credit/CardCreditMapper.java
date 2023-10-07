package com.rayllanderson.raybank.card.services.credit;

import com.rayllanderson.raybank.shared.dtos.Type;
import com.rayllanderson.raybank.transaction.models.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring", imports = Type.class)
public interface CardCreditMapper {

    @Mapping(target = "cardId", source = "transaction.debit.id")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "origin.identifier", source = "transaction.credit.id")
    @Mapping(target = "origin.type", expression = "java(Type.REFUND)")
    @Mapping(target = "origin.referenceTransactionId", source = "transaction.id")
    CardCreditInput refundInput(Transaction transaction, BigDecimal amount);
}
