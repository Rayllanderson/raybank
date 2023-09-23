package com.rayllanderson.raybank.card.services.credit;

import com.rayllanderson.raybank.card.models.CreditCard;
import com.rayllanderson.raybank.card.repository.CreditCardRepository;
import com.rayllanderson.raybank.exceptions.NotFoundException;
import com.rayllanderson.raybank.statement.aop.CreateStatement;
import com.rayllanderson.raybank.transaction.models.card.CardCreditTransaction;
import com.rayllanderson.raybank.transaction.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardCreditService {

    private final CreditCardRepository creditCardRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    @CreateStatement
    public CardCreditTransaction credit(final CardCreditInput cardCreditInput) {
        final CreditCard creditCard = creditCardRepository.findById(cardCreditInput.getCardId())
                .orElseThrow(() -> new NotFoundException("Cartão de crédito inexistente"));

        creditCard.credit(cardCreditInput.toDomainInput());

        return transactionRepository.save(CardCreditTransaction.from(cardCreditInput));
    }
}
