package com.rayllanderson.raybank.e2e;

import com.rayllanderson.raybank.e2e.containers.postgres.PostgresContainer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ActiveProfiles("e2e-test")
@SpringBootTest
@ExtendWith(PostgresCleanUpExtension.class)
@AutoConfigureMockMvc
@PostgresContainer(schema = "raybank")
@Tag("e2eTest")
public @interface E2ETest {
}
