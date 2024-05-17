package com.rayllanderson.raybank.users.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class KeycloakEventConsumer {

    private final ObjectMapper objectMapper;
    private final List<KeycloakConsumerService> keycloakConsumerServices;

    @SqsListener("${sqs.queue-name}")
    public void listen(final String message) throws Exception {
        log.info("chegou mensagem aqui: {}", message);
        try {
            final var request = objectMapper.readValue(message, KeycloakSQSRequest.class);

            final Optional<KeycloakConsumerService> possibleService = keycloakConsumerServices.stream().filter(s -> s.supports(request)).findFirst();

            possibleService.ifPresent(service -> service.process(request));
        } catch (Exception e) {
            log.error("Failed to consume register user request {}",message, e);
            throw e;
        }
    }
}
