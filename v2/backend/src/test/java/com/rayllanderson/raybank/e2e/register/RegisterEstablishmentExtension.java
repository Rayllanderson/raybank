package com.rayllanderson.raybank.e2e.register;

import com.rayllanderson.raybank.e2e.factory.BankAccountCreator;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.util.AnnotationUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class RegisterEstablishmentExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(final ExtensionContext extensionContext) {
        ApplicationContext applicationContext = SpringExtension.getApplicationContext(extensionContext);
        BankAccountCreator bankAccountCreator = applicationContext.getBean(BankAccountCreator.class);

        final var registerAnnotation = AnnotationUtils.findAnnotation(extensionContext.getRequiredTestMethod(), RegisterEstablishment.class);

        registerAnnotation.ifPresent(annotation -> bankAccountCreator.newEstablishmentBankAccount(annotation.id(), annotation.status()));
    }
}
