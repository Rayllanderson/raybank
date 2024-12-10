package com.rayllanderson.raybank.utils;

import lombok.NoArgsConstructor;

import java.time.Instant;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class InstantUtil {

    public static final Instant MAX_SUPPORTED_INSTANT = Instant.parse("9999-12-31T23:59:59Z");
}
