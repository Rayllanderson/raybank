package com.rayllanderson.raybank.users.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rayllanderson.raybank.users.services.register.RegisterUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegisterUserConsumer {

    private final RegisterUserService registerUserService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${kafka.register-user-topic}", containerFactory = "customKafkaListenerContainerFactory")
    public void register(@Payload String message, Acknowledgment ack) {
        try {
            final var request = objectMapper.readValue(message, RegisterUserFromKeycloackRequest.class);

            if (isRegister(request)) {
                log.info("received register request from realmId:{} and userId: {}", request.realmId(), request.userId());
                registerUserService.register(request.toUserInput());
            }
            if (isCreate(request)) {
                log.info("received create request from realmId:{} and userId: {}", request.realmId(), request.userId());
                registerUserService.preRegisterEstablishemnt(request.toEstablismentInput());
            }

        } catch (Exception e) {
            log.error("Failed to consume register user request", e);
        } finally {
            ack.acknowledge();
        }
    }

    private static boolean isRegister(RegisterUserFromKeycloackRequest request) {
        return request.type().equalsIgnoreCase("REGISTER");
    }

    private static boolean isCreate(RegisterUserFromKeycloackRequest request) {
        return request.type().equalsIgnoreCase("CREATE") && request.resourcePath() != null;
    }
}
