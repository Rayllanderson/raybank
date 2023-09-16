package com.rayllanderson.raybank.card.services.create;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum DueDays {

    _1(1), _6(6), _12(12), _17(17), _24(24);

    private final Integer day;

    DueDays(Integer day) {
        this.day = day;
    }

    public static DueDays of(Object o) {
        for (DueDays value : values()) {
            if (value.day.equals(o))
                return value;
        }
        final var availableDays = Arrays.stream(DueDays.values()).map(DueDays::getDay).toString();
        throw new IllegalArgumentException("Dia de vencimento inválido. Por favor, selecione um dos seguintes dias: " + availableDays);
    }
}
