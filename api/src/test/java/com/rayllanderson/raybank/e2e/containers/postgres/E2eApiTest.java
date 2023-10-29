package com.rayllanderson.raybank.e2e.containers.postgres;

import com.rayllanderson.raybank.e2e.HttpPeform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;

public abstract class E2eApiTest implements HttpPeform {

    @Autowired
    private MockMvc mvc;

    @Override
    public MockMvc mvc() {
        return mvc;
    }

    @DynamicPropertySource
    public static void setDatasourceProperties(final DynamicPropertyRegistry registry) {
        registry.add("postgres.host", () -> PostgresContainerUtil.getPostgres().getHost());
        registry.add("postgres.port", () -> PostgresContainerUtil.getPostgres().getMappedPort(5432));
    }
}
