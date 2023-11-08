package com.rayllanderson.raybank.e2e.containers.postgres;

import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(PostgresTestExtension.class)
public @interface PostgresContainer {
    String username() default "postgres";
    String password() default "12345";
    String schema();
}
