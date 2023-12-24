package com.rayllanderson.raybank.installment.repository;

import com.rayllanderson.raybank.installment.models.InstallmentPlan;

public interface InstallmentPlanGateway {
    InstallmentPlan findById(String id);
}