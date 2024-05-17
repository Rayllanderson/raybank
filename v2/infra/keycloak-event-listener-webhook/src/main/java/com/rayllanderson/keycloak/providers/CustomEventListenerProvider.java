package com.rayllanderson.keycloak.providers;

import com.rayllanderson.kafka.KafkaProducer;
import com.rayllanderson.sqs.SqsEventPublisher;
import org.jboss.logging.Logger;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.admin.AdminEvent;

import java.util.Set;

public class CustomEventListenerProvider implements EventListenerProvider {

    private static final Logger log = Logger.getLogger(CustomEventListenerProvider.class);
    private final Set<Object> allowedEvents;
    private final SqsEventPublisher sqsEventPublisher;

    public CustomEventListenerProvider(SqsEventPublisher sqsEventPublisher, Set<Object> allowEvents) {
        this.allowedEvents = allowEvents;
        this.sqsEventPublisher = sqsEventPublisher;
    }

    @Override
    public void onEvent(Event event) {
        if (allowedEvents == null || allowedEvents.contains(event.getType())) {
            log.info("publicou evento do tipo: " + event.getType());
            this.sqsEventPublisher.publishEvent(event);
        }
    }

    @Override
    public void onEvent(AdminEvent event, boolean includeRepresentation) {
        if (allowedEvents == null || allowedEvents.contains(event.getOperationType())) {
            log.info("publicou evento do tipo: " + event.getOperationType());
            this.sqsEventPublisher.publishEvent(event);
        }
    }

    private void sendKafkaRequest(String stringEvent) {
        KafkaProducer.publish(stringEvent);
    }

    @Override
    public void close() {
    }

}
