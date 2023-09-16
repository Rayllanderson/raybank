package com.rayllanderson.raybank.card.events;

import com.rayllanderson.raybank.event.Event;

import java.time.LocalDateTime;

public class CreditCardCreatedEvent implements Event {

    private final String id;
    private final LocalDateTime ocurredOn;

    public CreditCardCreatedEvent(String id) {
        this.id = id;
        this.ocurredOn = LocalDateTime.now();
    }

    public String id() {
        return id;
    }

    @Override
    public LocalDateTime ocurredOn() {
        return ocurredOn;
    }
}
