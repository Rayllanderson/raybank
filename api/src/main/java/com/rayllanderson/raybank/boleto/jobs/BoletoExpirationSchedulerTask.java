package com.rayllanderson.raybank.boleto.jobs;

import com.rayllanderson.raybank.boleto.services.expire.BoletoExpirationService;
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
public class BoletoExpirationSchedulerTask {

    private final BoletoExpirationService boletoExpirationService;

    @Async
    @Scheduled(fixedDelayString = "${boleto.scheduler.expirate}", timeUnit = TimeUnit.SECONDS)
    @SchedulerLock(name = "BoletoExpiration_ScheduleTask", lockAtLeastFor = "${boleto.lock.atLeastFor}", lockAtMostFor = "${boleto.lock.atMostFor}")
    public void process() {
        LockAssert.assertLocked();

        log.info("fetching all boletos to expire, {}", LocalDateTime.now());

        boletoExpirationService.expire();

        log.info("completed 'boleto expire' scheduler task -> {}", LocalDateTime.now());
    }
}
