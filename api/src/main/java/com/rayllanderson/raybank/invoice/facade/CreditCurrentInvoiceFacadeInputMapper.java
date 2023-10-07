package com.rayllanderson.raybank.invoice.facade;

import com.rayllanderson.raybank.invoice.util.InvoiceCreditDescriptionUtil;
import com.rayllanderson.raybank.transaction.models.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring", imports = InvoiceCreditDescriptionUtil.class)
public interface CreditCurrentInvoiceFacadeInputMapper {

    @Mapping(target = "cardId", source = "transaction.debit.id")
    @Mapping(target = "transactionId", source = "transaction.id")
    @Mapping(target = "description", expression = "java(InvoiceCreditDescriptionUtil.fromRefund(transaction.getDescription()))")
    CreditCurrentInvoiceFacadeInput from(final Transaction transaction, final BigDecimal amountToBeCredited);
}
