package com.rayllanderson.raybank.contact.aop;

import com.rayllanderson.raybank.contact.service.AddContactInput;
import com.rayllanderson.raybank.contact.service.AddContactService;
import com.rayllanderson.raybank.contact.service.ContactAccountType;
import com.rayllanderson.raybank.statement.services.create.CreateStatementServiceFactory;
import com.rayllanderson.raybank.transaction.models.Transaction;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@Aspect
@EnableAspectJAutoProxy
@RequiredArgsConstructor
public class AddContactAspect {

    private final AddContactService addContactService;

    @AfterReturning(
            pointcut = "@annotation(com.rayllanderson.raybank.contact.aop.AddCreditAccountAsContact)",
            returning = "transaction")
    public void addContact(final Transaction transaction) {
        final var contact = new AddContactInput(transaction.getAccountId(), transaction.getCredit().getId(), transaction.getMethod());

        addContactService.add(contact);
    }

}
