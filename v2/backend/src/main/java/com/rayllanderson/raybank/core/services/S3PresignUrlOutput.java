package com.rayllanderson.raybank.core.services;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;

public record S3PresignUrlOutput(URL url, Instant expiration) {
}
