package com.rayllanderson.raybank.statement.services.create;

import com.rayllanderson.raybank.card.transactions.payment.CardCreditPaymentTransaction;
import com.rayllanderson.raybank.statement.models.BankStatement;
import com.rayllanderson.raybank.transaction.models.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring", imports = UUID.class)
public interface CreateBankStatementMapper {

    @Mapping(target = "id", expression = "java(UUID.randomUUID().toString())")
    @Mapping(target = "transactionId", source = "transaction.id")
    @Mapping(target = "credit", source = "credit")
    @Mapping(target = "debit", source = "debit")
    BankStatement from(Transaction transaction, BankStatement.Credit credit, BankStatement.Debit debit);

    @Mapping(target = "id", expression = "java(UUID.randomUUID().toString())")
    @Mapping(target = "transactionId", source = "transaction.id")
    @Mapping(target = "installmentPlan.id", source = "transaction.planId")
    @Mapping(target = "installmentPlan.installments", source = "transaction.installments")
    @Mapping(target = "credit", source = "credit")
    @Mapping(target = "debit", source = "debit")
    BankStatement from(CardCreditPaymentTransaction transaction, BankStatement.Credit credit, BankStatement.Debit debit);
}
