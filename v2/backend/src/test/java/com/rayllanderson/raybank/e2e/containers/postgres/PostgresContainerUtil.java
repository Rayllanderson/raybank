package com.rayllanderson.raybank.e2e.containers.postgres;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;


public final class PostgresContainerUtil {

    private static PostgreSQLContainer<?> postgres;
    private static final String POSTGRES_IMAGE = "postgres:15-alpine";

    public static void start(String username, String password, String database) {
        postgres = new PostgreSQLContainer<>(DockerImageName.parse(POSTGRES_IMAGE))
                .withUsername(username)
                .withPassword(password)
                .withDatabaseName(database);
        postgres.start();
    }

    public static PostgreSQLContainer<?> getPostgres() {
        return postgres;
    }
}
