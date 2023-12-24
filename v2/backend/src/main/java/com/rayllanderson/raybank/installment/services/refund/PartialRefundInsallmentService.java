package com.rayllanderson.raybank.installment.services.refund;

import com.rayllanderson.raybank.installment.models.InstallmentPlan;
import com.rayllanderson.raybank.installment.repository.InstallmentPlanGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PartialRefundInsallmentService {

    private final InstallmentPlanGateway installmentPlanGateway;

    @Transactional
    public void process(final PartialRefundInsallmentInput input) {
        final InstallmentPlan plan = installmentPlanGateway.findById(input.getPlanId());

        plan.partialRefund(input.getAmount());
    }
}
