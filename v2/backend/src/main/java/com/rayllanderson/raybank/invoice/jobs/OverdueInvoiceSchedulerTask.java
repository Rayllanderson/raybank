package com.rayllanderson.raybank.invoice.jobs;

import com.rayllanderson.raybank.invoice.services.scheduler.OverdueInvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.LockAssert;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
@EnableSchedulerLock(defaultLockAtMostFor = "30S")
public class OverdueInvoiceSchedulerTask {

    private final OverdueInvoiceService overdueInvoiceService;

    @Async
    @Scheduled(fixedDelayString = "${invoice.scheduler.overdue}", timeUnit = TimeUnit.SECONDS)
    @SchedulerLock(name = "OverdueInvoice_ScheduleTask", lockAtLeastFor = "${invoice.lock.atLeastFor}", lockAtMostFor = "${invoice.lock.atMostFor}")
    public void process() {
        LockAssert.assertLocked();

        log.debug("fetching all invoices to overdue, {}", LocalDateTime.now());

        overdueInvoiceService.execute();

        log.debug("completed overdue scheduler task -> {}", LocalDateTime.now());
    }
}
