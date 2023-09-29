package com.rayllanderson.raybank.installment.services;

import com.rayllanderson.raybank.invoice.services.ProcessInvoiceInput;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateInstallmentPlanMapper {

    CreateInstallmentPlanInput from(ProcessInvoiceInput input, String invoiceId);
}
