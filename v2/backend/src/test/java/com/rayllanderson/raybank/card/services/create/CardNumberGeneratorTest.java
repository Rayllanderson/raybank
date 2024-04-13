package com.rayllanderson.raybank.card.services.create;

import com.rayllanderson.raybank.card.gateway.CardGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CardNumberGeneratorTest {

    @Mock
    private CardGateway cardGateway;

    @InjectMocks
    private CardNumberGenerator cardNumberGenerator;

    @Test
    void shouldGenerateCardNumber() {
        final long generatedNumber = cardNumberGenerator.generate();

        assertThat(String.valueOf(generatedNumber)).hasSize(16);
        assertThat(String.valueOf(generatedNumber)).startsWith("55");
    }

}