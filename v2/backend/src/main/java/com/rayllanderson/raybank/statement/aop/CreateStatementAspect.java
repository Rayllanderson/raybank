package com.rayllanderson.raybank.statement.aop;

import com.rayllanderson.raybank.statement.services.create.CreateBankStatementService;
import com.rayllanderson.raybank.transaction.models.Transaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@RequiredArgsConstructor
@Component
public class CreateStatementAspect {
    private final CreateBankStatementService createBankStatementService;

    @Pointcut("execution(* com.rayllanderson.raybank.transaction.repositories.TransactionRepository+.save(*))")
    public void saveTransaction(){}

    @AfterReturning(value = "saveTransaction()", returning = "transaction")
    public void create(Transaction transaction) {
        createBankStatementService.createFrom(transaction);
    }

}
