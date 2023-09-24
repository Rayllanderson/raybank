package com.rayllanderson.raybank.card.services.credit;

import com.rayllanderson.raybank.card.models.CreditCard;
import com.rayllanderson.raybank.card.repository.CreditCardRepository;
import com.rayllanderson.raybank.card.transactions.credit.CardCreditTransaction;
import com.rayllanderson.raybank.exceptions.NotFoundException;
import com.rayllanderson.raybank.transaction.models.Transaction;
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
    public Transaction credit(final CardCreditInput cardCreditInput) {
        final CreditCard creditCard = creditCardRepository.findById(cardCreditInput.getCardId())
                .orElseThrow(() -> new NotFoundException(String.format("card %s was not found", cardCreditInput.getCardId())));

        final Transaction originalTransaction = getReferenceTransaction(cardCreditInput, creditCard);

        creditCard.credit(cardCreditInput.toDomainInput());

        final Transaction cardCreditTransaction = CardCreditTransaction.from(cardCreditInput, creditCard.getAccountId(), originalTransaction.getId());
        return transactionRepository.save(cardCreditTransaction);
    }

    private Transaction getReferenceTransaction(CardCreditInput cardCreditInput, CreditCard creditCard) {
        return transactionRepository.findById(cardCreditInput.getReferenceTransactionId())
                .orElseThrow(() -> new NotFoundException(String.format("No Reference Transaction with id %s were found to credit the card %s",
                        cardCreditInput.getReferenceTransactionId(), creditCard.getId())));
    }
}
