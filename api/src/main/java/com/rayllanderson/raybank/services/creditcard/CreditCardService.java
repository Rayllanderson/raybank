package com.rayllanderson.raybank.services.creditcard;

import com.rayllanderson.raybank.exceptions.BadRequestException;
import com.rayllanderson.raybank.exceptions.NotFoundException;
import com.rayllanderson.raybank.exceptions.UnprocessableEntityException;
import com.rayllanderson.raybank.models.CreditCard;
import com.rayllanderson.raybank.statement.models.BankStatement;
import com.rayllanderson.raybank.repositories.BankAccountRepository;
import com.rayllanderson.raybank.repositories.CreditCardRepository;
import com.rayllanderson.raybank.services.creditcard.inputs.CreateCreditCardInput;
import com.rayllanderson.raybank.services.creditcard.inputs.PayInvoiceInput;
import com.rayllanderson.raybank.utils.NumberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;

@RequiredArgsConstructor
@Service
public class CreditCardService {

    private final CreditCardRepository creditCardRepository;
    private final BankAccountRepository bankAccountRepository;

    @Transactional
    public CreditCard createCreditCard(final CreateCreditCardInput input){
        final var bankAccount = bankAccountRepository.findByUserId(input.getUserId())
                .orElseThrow(() -> new BadRequestException("Conta bancária não disponível"));

        if (creditCardRepository.existsByBankAccountId(bankAccount.getId()))
            throw new UnprocessableEntityException("Já existe um cartão para o usuário");

        var creditCardToBeSaved = CreditCard.create(this.generateCreditCardNumber(),
                input.getLimit(),
                generateSecurityCode(),
                generateExpiryDate(),
                input.getDueDay().getDay(),
                bankAccount);
        creditCardToBeSaved = creditCardRepository.save(creditCardToBeSaved);

        bankAccount.setCreditCard(creditCardToBeSaved);
        bankAccountRepository.flush();

        return creditCardToBeSaved;
    }

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

    private long generateCreditCardNumber() {
        boolean isCardNumberInvalid;
        long generatedNumber;
        final int NUMBER_OF_DIGITS = 16;
        do {
            generatedNumber = NumberUtil.generateRandom(NUMBER_OF_DIGITS);
            isCardNumberInvalid = creditCardRepository.existsByNumber(generatedNumber) && (Long.toString(generatedNumber).length() != NUMBER_OF_DIGITS);
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
