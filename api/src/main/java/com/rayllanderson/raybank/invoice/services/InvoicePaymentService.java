package com.rayllanderson.raybank.invoice.services;

import com.rayllanderson.raybank.exceptions.NotFoundException;
import com.rayllanderson.raybank.repositories.BankAccountRepository;
import com.rayllanderson.raybank.card.repository.CreditCardRepository;
import com.rayllanderson.raybank.statement.models.BankStatement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InvoicePaymentService {

    private final CreditCardRepository creditCardRepository;
    private final BankAccountRepository bankAccountRepository;

    @Transactional
    public BankStatement payCurrentInvoice(final PayInvoiceInput input) {
        final var creditCard = this.creditCardRepository.findByBankAccountUserId(input.getUserId())
                .orElseThrow(() -> new NotFoundException("cartão não encontrado"));

        final var bankStatement = creditCard.payCurrentInvoice(input.getAmount());

        bankAccountRepository.save(creditCard.getBankAccount());
        creditCardRepository.save(creditCard);

        return bankStatement;
    }

    @Transactional
    public BankStatement payInvoiceById(final PayInvoiceInput input) {
        final var creditCard = this.creditCardRepository.findByBankAccountUserId(input.getUserId())
                .orElseThrow(() -> new NotFoundException("cartão não encontrado"));

        final var bankStatement = creditCard.payInvoiceById(input.getInvoiceId(), input.getAmount());

        bankAccountRepository.save(creditCard.getBankAccount());
        creditCardRepository.save(creditCard);

        return bankStatement;
    }
}
