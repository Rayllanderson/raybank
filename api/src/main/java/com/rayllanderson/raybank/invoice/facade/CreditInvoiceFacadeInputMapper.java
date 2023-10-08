package com.rayllanderson.raybank.invoice.facade;

import com.rayllanderson.raybank.boleto.services.credit.BoletoCreditInput;
import com.rayllanderson.raybank.invoice.constants.InvoiceCreditDescriptionConstant;
import com.rayllanderson.raybank.invoice.util.InvoiceCreditDescriptionUtil;
import com.rayllanderson.raybank.transaction.models.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring", imports = {InvoiceCreditDescriptionUtil.class, InvoiceCreditDescriptionConstant.class})
public interface CreditInvoiceFacadeInputMapper {

    @Mapping(target = "cardId", source = "transaction.debit.id")
    @Mapping(target = "transactionId", source = "transaction.id")
    @Mapping(target = "description", expression = "java(InvoiceCreditDescriptionUtil.fromRefund(transaction.getDescription()))")
    RefundInvoiceFacadeInput from(final Transaction transaction, final BigDecimal amountToBeCredited);

    @Mapping(target = "invoiceId", source = "beneficiaryId")
    @Mapping(target = "transactionId", source = "originalTransactionId")
    @Mapping(target = "description", expression = "java(InvoiceCreditDescriptionConstant.INVOICE_PAYMENT_DESCRIPTION)")
    CreditInvoiceFacadeInput from(final BoletoCreditInput boleto);
}
