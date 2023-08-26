package com.rayllanderson.raybank.utils;

import java.util.concurrent.ThreadLocalRandom;

public class NumberUtil {

    public static Long generateRandom(int digits){
        long min = (long) Math.pow(10, digits - 1);
        return ThreadLocalRandom.current().nextLong(min, min * 10);
    }
}
