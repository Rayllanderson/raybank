package com.rayllanderson.raybank.pix.service.limit.generate;

import com.rayllanderson.raybank.pix.gateway.PixGateway;
import com.rayllanderson.raybank.pix.model.PixLimit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeneratePixLimitService {

    private final PixGateway pixGateway;

    public void generate(final String accountId) {
        final var pixLimit = PixLimit.defaultLimit(accountId);
        pixGateway.save(pixLimit);
    }
}
