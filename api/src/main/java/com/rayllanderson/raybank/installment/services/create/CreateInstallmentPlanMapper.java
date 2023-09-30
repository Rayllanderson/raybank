package com.rayllanderson.raybank.installment.services.create;

import com.rayllanderson.raybank.invoice.services.processinstallment.ProcessInstallmentInvoiceInput;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateInstallmentPlanMapper {

    CreateInstallmentPlanInput from(ProcessInstallmentInvoiceInput input, String invoiceId);
}
