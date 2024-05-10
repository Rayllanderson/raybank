package com.rayllanderson.keycloak.providers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rayllanderson.kafka.Producer;
import org.jboss.logging.Logger;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.events.admin.OperationType;

import java.util.Set;

public class CustomEventListenerProvider implements EventListenerProvider {

    private static final Logger log = Logger.getLogger(CustomEventListenerProvider.class);
    private final Set<EventType> excludedEvents;
    private final Set<OperationType> excludedAdminOperations;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public CustomEventListenerProvider(Set<EventType> excludedEvents, Set<OperationType> excludedAdminOperations) {
        this.excludedEvents = excludedEvents;
        this.excludedAdminOperations = excludedAdminOperations;
    }

    @Override
    public void onEvent(Event event) {
        if (excludedEvents == null || !excludedEvents.contains(event.getType())) {
            String stringEvent = toString(event);
            log.info("Event to react to: " + stringEvent);
            this.sendKafkaRequest(stringEvent);
            log.debug("Event sent to kafka");
        }
    }

    @Override
    public void onEvent(AdminEvent event, boolean includeRepresentation) {
        if (excludedAdminOperations == null || !excludedAdminOperations.contains(event.getOperationType())) {
            String stringEvent = toString(event);
            log.info("Event to react to: " + stringEvent);
            this.sendKafkaRequest(stringEvent);
        }
    }

    private void sendKafkaRequest(String stringEvent) {
        Producer.publish(stringEvent);
    }

    private String toString(Event event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            log.error("Could not serialize JSON: " + e.getMessage());
            log.trace("Full stack trace: ", e);
            return "";
        }
    }

    private String toString(AdminEvent adminEvent) {
        try {
            return objectMapper.writeValueAsString(adminEvent);
        } catch (JsonProcessingException e) {
            log.error("Could not serialize JSON: " + e.getMessage());
            log.trace("Full stack trace: ", e);
            return "";
        }
    }

    @Override
    public void close() {
    }

}
