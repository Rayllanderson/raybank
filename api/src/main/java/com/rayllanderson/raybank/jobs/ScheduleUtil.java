package com.rayllanderson.raybank.jobs;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ScheduleUtil {

    public class Cron {
        public static final String EVERYDAY_00 = "0 0 0 1/1 * ? *"; //0 0 00 * * ?
        public static final String EVERY_MINUTE = "0 0/1 * * * ?";
        public static final String EVERY_FOUR_HOUR = "0 0 */4 ? * *";
    }
}
