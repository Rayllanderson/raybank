package com.rayllanderson.raybank.installment.repository;

import com.rayllanderson.raybank.exceptions.NotFoundException;
import com.rayllanderson.raybank.installment.models.InstallmentPlan;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InstallmentPlanPostgresGateway implements InstallmentPlanGateway {

    private final InstallmentPlanRepository installmentPlanRepository;

    @Override
    public InstallmentPlan findById(String id) {
        return installmentPlanRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Installment plan %s was not found", id)));
    }
}
