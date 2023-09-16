package com.rayllanderson.raybank.invoice.jobs;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ScheduleUtil {

    public static final String TWO_HOURS = "120M";

    public class Cron {
        public static final String EVERYDAY_00 = "0 0 0 1/1 * ? *"; //0 0 00 * * ?
        public static final String EVERY_MINUTE = "0 0/1 * * * ?";
        public static final String EVERY_TWO_HOURS = "0 0 */2 ? * *";
        public static final String EVERY_FOUR_HOURS = "0 0 */4 ? * *";
    }
}
