package com.rayllanderson.raybank.core.exceptions.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FieldError {
    private String field;
    private String message;
}
