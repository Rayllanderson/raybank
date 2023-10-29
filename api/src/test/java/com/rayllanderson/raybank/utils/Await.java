package com.rayllanderson.raybank.utils;

import org.awaitility.Awaitility;
import org.awaitility.Duration;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

public class Await {

    private Await(){}

    public static void await(int seconds) {
        Awaitility.await().pollDelay(new Duration(seconds, TimeUnit.SECONDS)).untilAsserted(() -> assertThat(true).isTrue());
    }

    public static void wait2ms() {
        Awaitility.await().pollDelay(new Duration(2L, TimeUnit.MILLISECONDS)).untilAsserted(() -> assertThat(true).isTrue());
    }
}
