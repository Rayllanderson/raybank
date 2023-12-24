package com.rayllanderson.raybank.installment.services.find;

import com.rayllanderson.raybank.installment.repository.InstallmentPlanGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindInstallmentPlanService {

    private final InstallmentPlanGateway installmentPlanGateway;
    private final FindInstallmentPlanMapper mapper;

    public InstallmentPlanOutput findById(String id) {
        return mapper.from(installmentPlanGateway.findById(id));
    }

}
