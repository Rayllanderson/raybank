package com.rayllanderson.raybank.jobs.invoice;

import com.rayllanderson.raybank.jobs.ScheduleUtil.Cron;
import com.rayllanderson.raybank.services.creditcard.CloseInvoiceService;
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
@EnableSchedulerLock(defaultLockAtMostFor = "1M")
public class CloseInvoiceSchedulerTask {

    private final CloseInvoiceService closeInvoiceService;

    @Async
    @Scheduled(cron = Cron.EVERY_MINUTE)
    @SchedulerLock(name = "CloseInvoice_ScheduleTask", lockAtLeastFor = "1M", lockAtMostFor = "1M")
    public void process() {
        LockAssert.assertLocked();

        log.debug("fetching all invoices to close, {}", LocalDateTime.now());

        closeInvoiceService.execute();

        log.debug("completed scheduler -> {}", LocalDateTime.now());
    }
}
