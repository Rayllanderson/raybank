package com.rayllanderson.raybank.invoice.services.find;

import com.rayllanderson.raybank.installment.models.Installment;
import com.rayllanderson.raybank.invoice.controllers.find.FindInvoiceListResponse;
import com.rayllanderson.raybank.invoice.controllers.find.FindInvoiceResponse;
import com.rayllanderson.raybank.invoice.models.Invoice;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface FindInvoiceMapper {

    List<FindInvoiceListOutput> from(Collection<Invoice> invoices);
    FindInvoiceOutput from(Invoice invoice);
    @Mapping(target = "planId", source = "installmentPlan.id")
    @Mapping(target = "occuredOn", expression = "java(installment.getUpatedOcurredOn())")
    FindInvoiceOutput.InstallmentOutput from(Installment installment);

    List<FindInvoiceListResponse> fromOutput(Collection<FindInvoiceListOutput> invoices);
    FindInvoiceResponse fromOutput(FindInvoiceOutput invoice);
    List<FindInvoiceResponse.InstallmentTransactionResponse> mapInstallments(List<FindInvoiceOutput.InstallmentOutput> installment);
    List<FindInvoiceResponse.CreditTransactionResponse> mapCredits(List<FindInvoiceOutput.CreditOutput> creditOutputs);
    @Mapping(target = "type", constant = "INSTALLMENT")
    FindInvoiceResponse.InstallmentTransactionResponse mapInstallment(FindInvoiceOutput.InstallmentOutput installment);
    @Mapping(target = "value", source = "amount")
    FindInvoiceResponse.CreditTransactionResponse mapCredit(FindInvoiceOutput.CreditOutput creditOutputs);

    @AfterMapping
    default void setTransactions(@MappingTarget FindInvoiceResponse response, FindInvoiceOutput output) {
        final var creditTransactions = mapCredits(output.getCredits());
        creditTransactions.removeIf(t -> t.getValue().compareTo(BigDecimal.ZERO) == 0);

        final var installmentsTransactions = mapInstallments(output.getInstallments());

        final var transactions = new ArrayList<FindInvoiceResponse.InvoiceTransactionResponse>(creditTransactions);

        transactions.addAll(installmentsTransactions);
        response.setTransactions(transactions.stream().sorted().collect(Collectors.toList()));
    }

}
