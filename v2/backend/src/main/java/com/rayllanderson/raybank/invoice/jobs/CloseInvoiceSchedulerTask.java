package com.rayllanderson.raybank.invoice.jobs;

import com.rayllanderson.raybank.invoice.services.scheduler.CloseInvoiceService;
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
public class CloseInvoiceSchedulerTask {

    private final CloseInvoiceService closeInvoiceService;

    @Async
    @Scheduled(fixedDelayString = "${invoice.scheduler.close}", timeUnit = TimeUnit.SECONDS)
    @SchedulerLock(name = "CloseInvoice_ScheduleTask", lockAtLeastFor = "${invoice.lock.atLeastFor}", lockAtMostFor = "${invoice.lock.atMostFor}")
    public void process() {
        LockAssert.assertLocked();

        log.debug("fetching all invoices to close, {}", LocalDateTime.now());

        closeInvoiceService.execute();

        log.debug("completed close scheduler task -> {}", LocalDateTime.now());
    }
}
