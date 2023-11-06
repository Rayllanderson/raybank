package com.rayllanderson.raybank.card.services.create;

import com.rayllanderson.raybank.core.exceptions.BadRequestException;
import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Collectors;

import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.INVALID_DUE_DATE;

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
        final var availableDays = Arrays.stream(DueDays.values()).map(DueDays::getDay).collect(Collectors.toList());
        throw BadRequestException.with(INVALID_DUE_DATE, "Dia de vencimento inválido. Por favor, selecione um dos seguintes dias: " + availableDays);
    }
}
