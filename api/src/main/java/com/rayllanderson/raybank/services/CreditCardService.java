package com.rayllanderson.raybank.services;

import com.rayllanderson.raybank.models.BankAccount;
import com.rayllanderson.raybank.models.CreditCard;
import com.rayllanderson.raybank.repositories.CreditCardRepository;
import com.rayllanderson.raybank.utils.NumberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CreditCardService {

    private final CreditCardRepository creditCardRepository;

    /**
     * Cria e salva um novo cartão de crédito.
     *
     * @param savedBankAccount conta do banco a receber novo cartão de crédito.
     * Esta, por sua vez, necessita estar salvo no banco dados
     */
    @Transactional
    public CreditCard createCreditCard(BankAccount savedBankAccount){
        var creditCardToBeSaved = CreditCard.builder()
                .cardNumber(this.generateCreditCardNumber())
                .bankAccount(savedBankAccount)
                .build();
        return creditCardRepository.save(creditCardToBeSaved);
    }

    private long generateCreditCardNumber() {
        boolean isCardNumberInvalid;
        long generatedNumber;
        final int NUMBER_OF_DIGITS = 16;
        do {
            generatedNumber = NumberUtil.generateRandom(NUMBER_OF_DIGITS);
            isCardNumberInvalid =
                    creditCardRepository.existsByCardNumber(generatedNumber) && (Long.toString(generatedNumber).length() != NUMBER_OF_DIGITS);
        } while (isCardNumberInvalid);
        return generatedNumber;
    }
}
