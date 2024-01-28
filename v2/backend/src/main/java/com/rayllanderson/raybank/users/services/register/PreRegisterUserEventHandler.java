package com.rayllanderson.raybank.users.services.register;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PreRegisterUserEventHandler {

    private final RegisterUserService registerUserService;

    @Transactional
    @Async
    @EventListener
    @Retryable(maxAttemptsExpression = "${retry.register.maxAttempts}", backoff = @Backoff(delayExpression = "${retry.register.maxDelay}"))
    public void handlerEvent(final TempUserCreatedEvent event) {
        registerUserService.registerEstablishment(event);
    }
}
