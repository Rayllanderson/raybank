package com.rayllanderson.raybank.installment.services.find;

import com.rayllanderson.raybank.installment.controller.InstallmentPlanResponse;
import com.rayllanderson.raybank.installment.models.Installment;
import com.rayllanderson.raybank.installment.models.InstallmentPlan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FindInstallmentPlanMapper {

    InstallmentPlanOutput from(InstallmentPlan plan);
    InstallmentPlanResponse from(InstallmentPlanOutput plan);

    @Mapping(target = "invoiceId", source = "invoice.id")
    InstallmentPlanOutput.InstallmentOutput installmentToInstallmentOutput(Installment installment);
}
