package com.rayllanderson.raybank.users.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rayllanderson.raybank.users.services.register.RegisterUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class KeycloakEventConsumer {

    private final ObjectMapper objectMapper;
    private final List<KeycloakConsumerService> keycloakConsumerServices;

    @KafkaListener(topics = "${kafka.topics.keycloak-event-listener-topic}", containerFactory = "customKafkaListenerContainerFactory")
    public void listen(@Payload String message, Acknowledgment ack) {
        try {
            final var request = objectMapper.readValue(message, KeycloackKafkaRequest.class);

            final Optional<KeycloakConsumerService> possibleService = keycloakConsumerServices.stream().filter(s -> s.supports(request)).findFirst();

            possibleService.ifPresent(service -> service.process(request));
        } catch (Exception e) {
            log.error("Failed to consume register user request", e);
        } finally {
            ack.acknowledge();
        }
    }
}
