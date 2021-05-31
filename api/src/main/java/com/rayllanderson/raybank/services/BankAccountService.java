package com.rayllanderson.raybank.services;

import com.rayllanderson.raybank.models.BankAccount;
import com.rayllanderson.raybank.models.User;
import com.rayllanderson.raybank.repositories.BankAccountRepository;
import com.rayllanderson.raybank.utils.NumberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final CreditCardService creditCardService;

    /**
     * Cria e salva uma nova conta bancária junta de um cartão de crédito.
     *
     * @param savedUser usuário a receber nova conta. Este, por sua vez, necessita estar salvo no banco dados
     * @return BankAccount salva com cartão de crédito
     */
    @Transactional
    public BankAccount createAccountBank(User savedUser) {
        int accountNumber = this.generateAccountNumber();
        var bankAccountToBeSaved = BankAccount.builder()
                .accountNumber(accountNumber)
                .user(savedUser).build();
        bankAccountToBeSaved = bankAccountRepository.save(bankAccountToBeSaved);
        bankAccountToBeSaved.setCreditCard(creditCardService.createCreditCard(bankAccountToBeSaved));
        return bankAccountRepository.save(bankAccountToBeSaved);
    }



    private int generateAccountNumber() {
        boolean isAccountNumberInvalid;
        int generatedNumber;
        final int NUMBER_OF_DIGITS = 9;
        do {
            generatedNumber = Integer.parseInt(Long.toString(NumberUtil.generateRandom(NUMBER_OF_DIGITS)));
            isAccountNumberInvalid = bankAccountRepository.existsByAccountNumber(generatedNumber)
                    && (Integer.toString(generatedNumber).length() != NUMBER_OF_DIGITS);
        } while (isAccountNumberInvalid);
        return generatedNumber;
    }
}
