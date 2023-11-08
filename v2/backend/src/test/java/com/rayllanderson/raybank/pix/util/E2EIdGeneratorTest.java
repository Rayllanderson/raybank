package com.rayllanderson.raybank.pix.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class E2EIdGeneratorTest {

    @Test
    void shouldGenerateE2E() {
        final var occuredOn = LocalDateTime.of(2023, 10, 14, 1, 1, 1);

        String e2e = E2EIdGenerator.generateE2E(occuredOn);

        assertThat(e2e).hasSize(32).startsWith("E1397129020231014");
    }

}