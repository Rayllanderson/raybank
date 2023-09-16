package com.rayllanderson.raybank.card.jobs.invoice;

import com.rayllanderson.raybank.card.jobs.ScheduleUtil;
import com.rayllanderson.raybank.card.services.invoice.scheduler.CloseInvoiceService;
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
public class CloseInvoiceSchedulerTask {

    private final CloseInvoiceService closeInvoiceService;

    @Async
    @Scheduled(cron = ScheduleUtil.Cron.EVERY_MINUTE)
    @SchedulerLock(name = "CloseInvoice_ScheduleTask", lockAtLeastFor = "29S", lockAtMostFor = "30S")
    public void process() {
        LockAssert.assertLocked();

        log.info("fetching all invoices to close, {}", LocalDateTime.now());

        closeInvoiceService.execute();

        log.info("completed close scheduler task -> {}", LocalDateTime.now());
    }
}
