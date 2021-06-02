package com.rayllanderson.raybank.services;

import com.rayllanderson.raybank.dtos.requests.bank.CreditCardDto;
import com.rayllanderson.raybank.exceptions.BadRequestException;
import com.rayllanderson.raybank.models.BankAccount;
import com.rayllanderson.raybank.models.CreditCard;
import com.rayllanderson.raybank.repositories.BankAccountRepository;
import com.rayllanderson.raybank.repositories.CreditCardRepository;
import com.rayllanderson.raybank.utils.NumberUtil;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.And;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class CreditCardService {

    private final CreditCardRepository creditCardRepository;
    private final BankAccountRepository bankAccountRepository;

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
                .balance(new BigDecimal(5000))
                .invoice(BigDecimal.ZERO)
                .build();
        return creditCardRepository.save(creditCardToBeSaved);
    }

    @Transactional
    public void makePurchase(CreditCardDto dto){
        CreditCard creditCard = findByAccountId(dto.getAccount().getId());
        creditCard.makePurchase(dto.getAmount());
        creditCardRepository.save(creditCard);
    }

    @Transactional
    public void payInvoice(CreditCardDto dto){
        CreditCard creditCard = findByAccountId(dto.getAccount().getId());
        try {
            creditCard.payTheInvoice(dto.getAmount());
        } catch (IllegalArgumentException e){
            creditCard.payInvoiceAndRefundRemaining(dto.getAmount());
            bankAccountRepository.save(creditCard.getBankAccount());
        }finally {
            creditCardRepository.save(creditCard);
        }
    }

    public CreditCard findByAccountId(Long accountId){
        return creditCardRepository.findByBankAccountId(accountId)
                .orElseThrow(()-> new BadRequestException("Este cartão de crédito não existe"));
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
