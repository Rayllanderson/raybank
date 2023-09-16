package com.rayllanderson.raybank.aop;

import com.rayllanderson.raybank.models.transaction.Transaction;
import com.rayllanderson.raybank.services.statements.CreateStatementService;
import com.rayllanderson.raybank.services.statements.CreateStatementServiceFactory;
import com.rayllanderson.raybank.services.statements.CreateStatementServiceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Configuration
@Aspect
@EnableAspectJAutoProxy
@RequiredArgsConstructor
public class CreateStatementAspect {

    private final CreateStatementServiceFactory statementServiceFactory;

    @AfterReturning(
            pointcut = "@annotation(com.rayllanderson.raybank.aop.CreateStatement)",
            returning = "transaction")
    public void createStatement(final Transaction transaction) {
        final CreateStatementService statementService = statementServiceFactory.getStatementService(transaction);

        statementService.process(transaction);
    }

}
