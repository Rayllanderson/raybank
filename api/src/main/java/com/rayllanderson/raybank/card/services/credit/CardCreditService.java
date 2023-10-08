package com.rayllanderson.raybank.card.services.credit;

import com.rayllanderson.raybank.card.models.Card;
import com.rayllanderson.raybank.card.repository.CardRepository;
import com.rayllanderson.raybank.card.transactions.credit.CardCreditTransaction;
import com.rayllanderson.raybank.core.exceptions.NotFoundException;
import com.rayllanderson.raybank.transaction.models.Transaction;
import com.rayllanderson.raybank.transaction.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardCreditService {

    private final CardRepository cardRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    public Transaction credit(final CardCreditInput cardCreditInput) {
        final Card card = cardRepository.findById(cardCreditInput.getCardId())
                .orElseThrow(() -> new NotFoundException(String.format("card %s was not found", cardCreditInput.getCardId())));

        final Transaction originalTransaction = getReferenceTransaction(cardCreditInput);

        card.credit(cardCreditInput.toDomainInput());

        final Transaction cardCreditTransaction = CardCreditTransaction.from(cardCreditInput, card.getAccountId(), originalTransaction.getId());
        return transactionRepository.save(cardCreditTransaction);
    }

    private Transaction getReferenceTransaction(CardCreditInput cardCreditInput) {
        final String referenceTransactionId = cardCreditInput.getOrigin().getReferenceTransactionId();
        return transactionRepository.findById(referenceTransactionId)
                .orElseThrow(() -> new NotFoundException(String.format("No Reference Transaction with id %s were found to refund the card %s",
                        referenceTransactionId, cardCreditInput.getCardId())));
    }
}
