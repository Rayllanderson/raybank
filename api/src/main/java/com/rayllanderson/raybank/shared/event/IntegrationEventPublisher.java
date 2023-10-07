package com.rayllanderson.raybank.core.configuration.event;

public interface IntegrationEventPublisher {
    void publish(Event event);
}
