package com.rayllanderson.raybank.exceptions.handler;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class StandardError {
    protected LocalDateTime timestamp;
    protected Integer status;
    protected String title;
    protected Object message;
    protected String path;
}
