package com.rayllanderson.raybank.e2e.security;

import com.rayllanderson.raybank.e2e.constants.Constants;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@WithSecurityContext(factory = WithEstablishmentUserSecurityContextFactory.class)
public @interface WithEstablishmentUser {
    String id() default Constants.ESTABLISHMENT_ID;
}
