package com.rayllanderson.raybank.invoice.services.find;

import com.rayllanderson.raybank.installment.models.Installment;
import com.rayllanderson.raybank.invoice.controllers.find.FindInvoiceListResponse;
import com.rayllanderson.raybank.invoice.controllers.find.FindInvoiceResponse;
import com.rayllanderson.raybank.invoice.models.Invoice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface FindInvoiceMapper {

    List<FindInvoiceOutput> from(Collection<Invoice> invoices);
    FindInvoiceOutput from(Invoice invoice);
    @Mapping(target = "planId", source = "installment.installmentPlan.id")
    FindInvoiceOutput.InstallmentOutput from(Installment installment);
    List<FindInvoiceListResponse> fromOutput(Collection<FindInvoiceOutput> invoices);
    FindInvoiceResponse fromOutput(FindInvoiceOutput invoice);

}
