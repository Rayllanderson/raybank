package com.rayllanderson.raybank.installment.services.create;

import com.rayllanderson.raybank.installment.models.InstallmentPlan;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public record CreateInstallmentPlanOutput(String id,
                                          BigDecimal total,
                                          BigDecimal installmentValue,
                                          List<InstallmentOutput> installments) {

    record InstallmentOutput(String id, LocalDate dueDate) {}

    public static CreateInstallmentPlanOutput fromPlan(final InstallmentPlan plan) {
        return new CreateInstallmentPlanOutput(plan.getId(),
                plan.getTotal(),
                plan.getInstallmentValue(),
                plan.getInstallments()
                        .stream()
                        .map(i -> new InstallmentOutput(i.getId(), i.getDueDate()))
                        .collect(Collectors.toList())
        );
    }
}
