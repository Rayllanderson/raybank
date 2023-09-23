package com.rayllanderson.raybank.statement.aop;

import com.rayllanderson.raybank.transaction.models.Transaction;
import com.rayllanderson.raybank.statement.services.create.CreateStatementService;
import com.rayllanderson.raybank.statement.services.create.CreateStatementServiceFactory;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@Aspect
@EnableAspectJAutoProxy
@RequiredArgsConstructor
public class CreateStatementAspect {

    private final CreateStatementServiceFactory statementServiceFactory;

    @AfterReturning(
            pointcut = "@annotation(com.rayllanderson.raybank.statement.aop.CreateStatement)",
            returning = "transaction")
    public void createStatement(final Transaction transaction) {
        final CreateStatementService statementService = statementServiceFactory.getStatementService(transaction);

        statementService.process(transaction);
    }

}
