package com.rayllanderson.raybank.jobs.invoice;

import com.rayllanderson.raybank.jobs.ScheduleUtil.Cron;
import com.rayllanderson.raybank.services.creditcard.CloseInvoiceService;
import com.rayllanderson.raybank.services.creditcard.OverdueInvoiceService;
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
    @Scheduled(cron = Cron.EVERY_MINUTE)
    @SchedulerLock(name = "OverdueInvoice_ScheduleTask", lockAtLeastFor = "29S", lockAtMostFor = "30S")
    public void process() {
        LockAssert.assertLocked();

        log.info("fetching all invoices to overdue, {}", LocalDateTime.now());

        overdueInvoiceService.execute();

        log.info("completed overdue scheduler task -> {}", LocalDateTime.now());
    }
}
