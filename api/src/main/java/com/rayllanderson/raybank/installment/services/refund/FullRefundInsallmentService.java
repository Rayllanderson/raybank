package com.rayllanderson.raybank.installment.services.refund;

import com.rayllanderson.raybank.installment.models.Installment;
import com.rayllanderson.raybank.installment.models.InstallmentPlan;
import com.rayllanderson.raybank.installment.repository.InstallmentPlanGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.rayllanderson.raybank.utils.SumUtils.sum;

@Service
@RequiredArgsConstructor
public class FullRefundInsallmentService {

    private final InstallmentPlanGateway installmentPlanGateway;

    @Transactional
    public FullRefundInsallmentOutput process(final String planId) {
        final InstallmentPlan plan = installmentPlanGateway.findById(planId);

        final var sumInstallmentsPaid = sum(plan.getInstallmentsPaid().stream().map(Installment::getValue));
        final var sumInstallmentsOverdue = sum(plan.getInstallmentsOverdue().stream().map(Installment::getPaidValue));
        final var totalSum = sumInstallmentsPaid.add(sumInstallmentsOverdue);

        plan.fullRefund();

        return new FullRefundInsallmentOutput(plan.getId(), totalSum);
    }
}
