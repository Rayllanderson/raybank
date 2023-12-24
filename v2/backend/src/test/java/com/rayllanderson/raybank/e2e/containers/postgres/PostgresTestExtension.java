package com.rayllanderson.raybank.e2e.containers.postgres;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.util.AnnotationUtils;

public class PostgresTestExtension implements BeforeAllCallback {

    @Override
    public void beforeAll(final ExtensionContext extensionContext) {
        final var postgresAnnotation = AnnotationUtils.findAnnotation(extensionContext.getRequiredTestClass(), PostgresContainer.class);
        postgresAnnotation.ifPresent(annotation -> PostgresContainerUtil.start(annotation.username(), annotation.password(), annotation.schema()));
    }
}
