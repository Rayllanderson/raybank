package com.rayllanderson.raybank.contact.aop;

import com.rayllanderson.raybank.contact.service.add.AddContactInput;
import com.rayllanderson.raybank.contact.service.add.AddContactService;
import com.rayllanderson.raybank.pix.service.transfer.PixTransferOutput;
import com.rayllanderson.raybank.transaction.models.Transaction;
import com.rayllanderson.raybank.transaction.models.TransactionMethod;
import lombok.RequiredArgsConstructor;
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

    @AfterReturning(
            pointcut = "@annotation(com.rayllanderson.raybank.contact.aop.AddCreditAccountAsContact)",
            returning = "pix")
    public void addContact(final PixTransferOutput pix) {
        final var contact = new AddContactInput(pix.getDebitAccountId(), pix.getE2eId(), TransactionMethod.PIX);

        addContactService.add(contact);
    }

}
