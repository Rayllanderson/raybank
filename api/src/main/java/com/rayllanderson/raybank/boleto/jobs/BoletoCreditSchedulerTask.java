package com.rayllanderson.raybank.boleto.jobs;

import com.rayllanderson.raybank.boleto.services.credit.BoletoCreditService;
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
public class BoletoCreditSchedulerTask {

    private final BoletoCreditService boletoCreditService;

    @Async
    @Scheduled(fixedDelayString = "${boleto.scheduler.process}", timeUnit = TimeUnit.SECONDS)
    @SchedulerLock(name = "BoletoCredit_ScheduleTask", lockAtLeastFor = "${boleto.lock.atLeastFor}", lockAtMostFor = "${boleto.lock.atMostFor}")
    public void process() {
        LockAssert.assertLocked();

        log.info("fetching all boletos to finalize payment, {}", LocalDateTime.now());

        boletoCreditService.credit();

        log.info("completed 'finalize boleto payment' scheduler task -> {}", LocalDateTime.now());
    }
}
