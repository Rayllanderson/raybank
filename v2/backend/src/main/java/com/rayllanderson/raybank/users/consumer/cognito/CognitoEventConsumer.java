package com.rayllanderson.raybank.users.consumer.cognito;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rayllanderson.raybank.users.services.register.RegisterUserService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static io.awspring.cloud.sqs.annotation.SqsListenerAcknowledgementMode.ALWAYS;

@Slf4j
@Component
@RequiredArgsConstructor
public class CognitoEventConsumer {

    private final ObjectMapper objectMapper;
    private final RegisterUserService registerUserService;

    @SqsListener(value = "${sqs.cognito-register-queue-name}", acknowledgementMode = ALWAYS)
    public void listen(final String message) throws Exception {
        try {
            log.info("received message {}", message);
            final CognitoUserRegistrationRequest request = objectMapper.readValue(message, CognitoUserRegistrationRequest.class);
            registerUserService.register(request.toUserInput());
        } catch (Exception e) {
            log.error("Failed to consume register user request {}",message, e);
            throw e;
        }
    }
}
