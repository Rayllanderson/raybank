package com.rayllanderson.raybank.installment.controller;

import com.rayllanderson.raybank.core.security.method.RequiredInstallmentPlanOwner;
import com.rayllanderson.raybank.installment.services.find.FindInstallmentPlanMapper;
import com.rayllanderson.raybank.installment.services.find.FindInstallmentPlanService;
import com.rayllanderson.raybank.installment.services.find.InstallmentPlanOutput;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "installment plan")
@RestController
@RequestMapping("api/v1/internal/installment-plans")
@RequiredArgsConstructor
public class FindInstallmentPlanController {

    private final FindInstallmentPlanService service;
    private final FindInstallmentPlanMapper mapper;

    @GetMapping("/{planId}")
    @RequiredInstallmentPlanOwner
    public ResponseEntity<?> find(@PathVariable String planId, @AuthenticationPrincipal Jwt jwt) {
        InstallmentPlanOutput output = service.findById(planId);
        return ResponseEntity.ok(mapper.from(output));
    }
}
