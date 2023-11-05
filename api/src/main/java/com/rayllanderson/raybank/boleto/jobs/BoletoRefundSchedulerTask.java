package com.rayllanderson.raybank.boleto.jobs;

import com.rayllanderson.raybank.boleto.services.refund.BoletoRefundService;
import com.rayllanderson.raybank.invoice.jobs.ScheduleUtil;
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
public class BoletoRefundSchedulerTask {

    private final BoletoRefundService boletoRefundService;

    @Async
    @Scheduled(fixedDelayString = "${boleto.scheduler.refund}", timeUnit = TimeUnit.SECONDS)
    @SchedulerLock(name = "BoletoRefund_ScheduleTask", lockAtLeastFor = "${boleto.lock.atLeastFor}", lockAtMostFor = "${boleto.lock.atMostFor}")
    public void process() {
        LockAssert.assertLocked();

        log.info("fetching all boletos to refund payment, {}", LocalDateTime.now());

        boletoRefundService.refundUnprocessed();

        log.info("completed 'refund boleto payment' scheduler task -> {}", LocalDateTime.now());
    }
}
