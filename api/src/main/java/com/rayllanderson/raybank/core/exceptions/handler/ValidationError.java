package com.rayllanderson.raybank.core.exceptions.handler;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ValidationError extends StandardError {
    private final String fields;
    private final String fieldsMessage;
}
