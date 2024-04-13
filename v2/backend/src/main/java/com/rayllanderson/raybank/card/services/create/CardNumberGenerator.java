package com.rayllanderson.raybank.card.services.create;

import com.rayllanderson.raybank.card.gateway.CardGateway;
import com.rayllanderson.raybank.utils.RandomUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CardNumberGenerator {

    private final CardGateway cardGateway;
    private static final long PREFIX = 55;

    public long generate() {
        boolean isCardNumberInvalid;
        long generatedNumber;
        final int NUMBER_OF_DIGITS = 16;
        do {
            generatedNumber = Long.parseLong(String.format("%s%s", PREFIX, RandomUtils.generate(14)));
            isCardNumberInvalid = cardGateway.existsByNumber(generatedNumber) && (Long.toString(generatedNumber).length() != NUMBER_OF_DIGITS);
        } while (isCardNumberInvalid);
        return generatedNumber;
    }
}
