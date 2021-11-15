package com.rayllanderson.raybank.external.token;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExternalTokenRepository extends JpaRepository<ExternalToken, Long> {
    boolean existsByClientName(String clientName);
}
