package com.rayllanderson.raybank.installment.services.create;

import com.rayllanderson.raybank.card.transactions.payment.CardPaymentTransaction;
import com.rayllanderson.raybank.invoice.services.processinstallment.ProcessInstallmentInvoiceInput;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CreateInstallmentPlanMapper {

    CreateInstallmentPlanInput from(ProcessInstallmentInvoiceInput input, String invoiceId);

    @Mapping(target = "transactionId", source = "id")
    @Mapping(target = "installmentCount", source = "installments")
    @Mapping(target = "establishmentId", source = "credit.id")
    @Mapping(target = "total", source = "amount")
    CreateInstallmentPlanInput from(CardPaymentTransaction input);
}
