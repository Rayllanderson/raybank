package com.rayllanderson.raybank.e2e.register;

import com.rayllanderson.raybank.bankaccount.model.BankAccountStatus;
import com.rayllanderson.raybank.e2e.constants.Constants;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(RegisterEstablishmentExtension.class)
public @interface RegisterEstablishment {
    String id() default Constants.ESTABLISHMENT_ID;
    BankAccountStatus status() default BankAccountStatus.ACTIVE;
}
