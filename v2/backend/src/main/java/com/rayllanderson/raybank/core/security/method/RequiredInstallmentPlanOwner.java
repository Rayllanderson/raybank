package com.rayllanderson.raybank.core.security.method;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("@methodSecurityChecker.checkPlan(#planId,#jwt)")
public @interface RequiredInstallmentPlanOwner {
}
