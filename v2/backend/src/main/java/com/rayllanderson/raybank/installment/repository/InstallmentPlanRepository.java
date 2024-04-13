package com.rayllanderson.raybank.installment.repository;

import com.rayllanderson.raybank.installment.models.InstallmentPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstallmentPlanRepository extends JpaRepository<InstallmentPlan, String> {
}