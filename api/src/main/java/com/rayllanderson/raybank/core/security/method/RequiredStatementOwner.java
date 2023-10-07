package com.rayllanderson.raybank.security.method;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("@methodSecurityChecker.checkStatement(#statementId,#jwt)")
public @interface RequiredStatementOwner {
}
