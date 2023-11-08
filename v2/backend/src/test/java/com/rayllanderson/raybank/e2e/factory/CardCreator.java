package com.rayllanderson.raybank.e2e.factory;

import com.rayllanderson.raybank.card.models.Card;
import com.rayllanderson.raybank.card.repository.CardRepository;
import com.rayllanderson.raybank.card.services.create.CreateCardService;
import com.rayllanderson.raybank.card.services.create.CreateCreditCardInput;
import com.rayllanderson.raybank.card.services.create.DueDays;
import com.rayllanderson.raybank.utils.Await;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CardCreator {

    @Autowired
    private CreateCardService createCardService;
    @Autowired
    private BankAccountCreator bankAccountCreator;

    public Card newCard() {
        final var account = bankAccountCreator.newNormalBankAccount();
        final var card = createCardService.createCreditCard(new CreateCreditCardInput(account.getId(), new BigDecimal("5000"), DueDays._6));

        Await.await(1); // to handler CreditCardCreatedEvent

        return card;
    }

    public Card newCard(String accountId) {
        final var account = bankAccountCreator.newNormalBankAccount(accountId);
        final var card = createCardService.createCreditCard(new CreateCreditCardInput(account.getId(), new BigDecimal("5000"), DueDays._6));

        Await.await(1); // to handler CreditCardCreatedEvent

        return card;
    }

}
