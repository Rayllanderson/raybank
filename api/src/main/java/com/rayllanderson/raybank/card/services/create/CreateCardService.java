package com.rayllanderson.raybank.card.services.create;

import com.rayllanderson.raybank.bankaccount.gateway.BankAccountGateway;
import com.rayllanderson.raybank.card.gateway.CardGateway;
import com.rayllanderson.raybank.card.models.Card;
import com.rayllanderson.raybank.core.exceptions.UnprocessableEntityException;
import com.rayllanderson.raybank.utils.RandomUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;

import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.CARD_ALREADY_REGISTERED;

@RequiredArgsConstructor
@Service
public class CreateCardService {

    private final CardGateway cardGateway;
    private final BankAccountGateway bankAccountGateway;

    @Transactional
    public Card createCreditCard(final CreateCreditCardInput input){
        final var bankAccount = bankAccountGateway.findById(input.getAccountId());

        if (cardGateway.existsByBankAccountId(bankAccount.getId()))
            throw UnprocessableEntityException.with(CARD_ALREADY_REGISTERED, "Já existe um cartão para o usuário");

        var creditCardToBeSaved = Card.create(this.generateCreditCardNumber(),
                input.getLimit(),
                generateSecurityCode(),
                generateExpiryDate(),
                input.getDueDay().getDay(),
                bankAccount);
        creditCardToBeSaved = cardGateway.save(creditCardToBeSaved);

        bankAccount.setCard(creditCardToBeSaved);
        bankAccountGateway.flush();

        return creditCardToBeSaved;
    }

    private long generateCreditCardNumber() {
        boolean isCardNumberInvalid;
        long generatedNumber;
        final int NUMBER_OF_DIGITS = 16;
        do {
            generatedNumber = RandomUtils.generate(NUMBER_OF_DIGITS);
            isCardNumberInvalid = cardGateway.existsByNumber(generatedNumber) && (Long.toString(generatedNumber).length() != NUMBER_OF_DIGITS);
        } while (isCardNumberInvalid);
        return generatedNumber;
    }

    private int generateSecurityCode() {
        return RandomUtils.generate(3).intValue();
    }

    private YearMonth generateExpiryDate() {
        return YearMonth.now().plusYears(8);
    }
}
