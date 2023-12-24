package com.rayllanderson.raybank.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.concurrent.ThreadLocalRandom;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RandomUtils {

    public static Long generate(int digits){
        long min = (long) Math.pow(10, digits - 1);
        return ThreadLocalRandom.current().nextLong(min, min * 10);
    }
}
