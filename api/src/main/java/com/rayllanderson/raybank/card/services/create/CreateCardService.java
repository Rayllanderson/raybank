package com.rayllanderson.raybank.card.services.create;

import com.rayllanderson.raybank.core.exceptions.BadRequestException;
import com.rayllanderson.raybank.core.exceptions.UnprocessableEntityException;
import com.rayllanderson.raybank.card.models.Card;
import com.rayllanderson.raybank.bankaccount.repository.BankAccountRepository;
import com.rayllanderson.raybank.card.repository.CardRepository;
import com.rayllanderson.raybank.utils.NumberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;

@RequiredArgsConstructor
@Service
public class CreateCardService {

    private final CardRepository cardRepository;
    private final BankAccountRepository bankAccountRepository;

    @Transactional
    public Card createCreditCard(final CreateCreditCardInput input){
        final var bankAccount = bankAccountRepository.findById(input.getAccountId())
                .orElseThrow(() -> new BadRequestException("Conta bancária não disponível"));

        if (cardRepository.existsByBankAccountId(bankAccount.getId()))
            throw new UnprocessableEntityException("Já existe um cartão para o usuário");

        var creditCardToBeSaved = Card.create(this.generateCreditCardNumber(),
                input.getLimit(),
                generateSecurityCode(),
                generateExpiryDate(),
                input.getDueDay().getDay(),
                bankAccount);
        creditCardToBeSaved = cardRepository.save(creditCardToBeSaved);

        bankAccount.setCard(creditCardToBeSaved);
        bankAccountRepository.flush();

        return creditCardToBeSaved;
    }

    private long generateCreditCardNumber() {
        boolean isCardNumberInvalid;
        long generatedNumber;
        final int NUMBER_OF_DIGITS = 16;
        do {
            generatedNumber = NumberUtil.generateRandom(NUMBER_OF_DIGITS);
            isCardNumberInvalid = cardRepository.existsByNumber(generatedNumber) && (Long.toString(generatedNumber).length() != NUMBER_OF_DIGITS);
        } while (isCardNumberInvalid);
        return generatedNumber;
    }

    private int generateSecurityCode() {
        return NumberUtil.generateRandom(3).intValue();
    }

    private YearMonth generateExpiryDate() {
        return YearMonth.now().plusYears(8);
    }
}
