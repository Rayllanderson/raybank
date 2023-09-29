package com.rayllanderson.raybank.installment.services;

import com.rayllanderson.raybank.installment.models.InstallmentPlan;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class CreateInstallmentPlanOutput {
    private final String id;
    private final BigDecimal total;
    private final BigDecimal installmentValue;
    private final List<InstallmentOutput> installments;

    @Getter
    @RequiredArgsConstructor
    public static class InstallmentOutput {
        private final String id;
        private final LocalDate dueDate;
    }

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
