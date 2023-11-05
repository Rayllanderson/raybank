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

@Slf4j
@Component
@RequiredArgsConstructor
@EnableSchedulerLock(defaultLockAtMostFor = "30S")
public class OverdueInvoiceSchedulerTask {

    private final OverdueInvoiceService overdueInvoiceService;

    @Async
    @Scheduled(cron = "0 0 */2 * * ?") //every 2 hours starting at 00am, of every day
    @SchedulerLock(name = "OverdueInvoice_ScheduleTask", lockAtLeastFor = "${invoice.lock.atLeastFor}", lockAtMostFor = "${invoice.lock.atMostFor}")
    public void process() {
        LockAssert.assertLocked();

        log.info("fetching all invoices to overdue, {}", LocalDateTime.now());

        overdueInvoiceService.execute();

        log.info("completed overdue scheduler task -> {}", LocalDateTime.now());
    }
}
