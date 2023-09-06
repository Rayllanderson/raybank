package com.rayllanderson.raybank.jobs.invoice;

import com.rayllanderson.raybank.jobs.ScheduleUtil.Cron;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.LockAssert;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@Slf4j
@Configuration
@EnableSchedulerLock(defaultLockAtMostFor = "5M")
public class CloseInvoiceSchedulerTask {

    @Scheduled(cron = Cron.EVERY_MINUTE)
    @SchedulerLock(name = "CloseInvoice_ScheduleTask", lockAtLeastFor = "1M", lockAtMostFor = "5M")
    public void process() {
        LockAssert.assertLocked();
        log.info("this is a test -> {}", LocalDateTime.now());
    }
}
