package com.rayllanderson.raybank.installment.repository;

import com.rayllanderson.raybank.core.exceptions.NotFoundException;
import com.rayllanderson.raybank.installment.models.InstallmentPlan;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.INSTALLMENTPLAN_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class InstallmentPlanPostgresGateway implements InstallmentPlanGateway {

    private final InstallmentPlanRepository installmentPlanRepository;

    @Override
    public InstallmentPlan findById(String id) {
        return installmentPlanRepository.findById(id)
                .orElseThrow(() -> NotFoundException.formatted(INSTALLMENTPLAN_NOT_FOUND, "Installment plan %s was not found", id));
    }
}
