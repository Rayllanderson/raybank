package com.rayllanderson.raybank.installment.services.create;

import com.rayllanderson.raybank.installment.models.Installment;
import com.rayllanderson.raybank.installment.models.InstallmentPlan;
import com.rayllanderson.raybank.installment.repository.InstallmentPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.rayllanderson.raybank.utils.DateManagerUtil.plusOneMonthKeepingCurrentDayOfMonth;
import static com.rayllanderson.raybank.utils.InstallmentUtil.createDescription;

@Service
@RequiredArgsConstructor
public class CreateInstallmentPlanService {

    private final InstallmentPlanRepository installmentPlanRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public CreateInstallmentPlanOutput create(final CreateInstallmentPlanInput input) {
        final InstallmentPlan installmentPlan = InstallmentPlan.create(input.getTransactionId(),
                input.getInvoiceId(),
                input.getEstablishmentId(),
                input.getInstallmentCount(),
                input.getTotal(),
                input.getDescription()
        );

        createInstallmentsAndAddToPlan(installmentPlan);

        installmentPlanRepository.save(installmentPlan);

        return CreateInstallmentPlanOutput.fromPlan(installmentPlan);
    }

    private void createInstallmentsAndAddToPlan(final InstallmentPlan plan) {
        var dueDate = plan.getCreatedAt().toLocalDate();
        for (int i = 0; i < plan.getInstallmentCount(); i++) {
            final String description = createDescription(plan.getDescription(), i + 1, plan.getInstallmentCount());
            final Installment installment = Installment.create(description, dueDate, plan);
            plan.addInstallment(installment);
            dueDate = plusOneMonthKeepingCurrentDayOfMonth(dueDate);
        }
    }
}
