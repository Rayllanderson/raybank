package com.rayllanderson.raybank.services;

import com.rayllanderson.raybank.dtos.requests.bank.BankTransferDto;
import com.rayllanderson.raybank.exceptions.BadRequestException;
import com.rayllanderson.raybank.models.BankAccount;
import com.rayllanderson.raybank.models.BankStatement;
import com.rayllanderson.raybank.models.User;
import com.rayllanderson.raybank.models.enums.StatementType;
import com.rayllanderson.raybank.repositories.BankAccountRepository;
import com.rayllanderson.raybank.repositories.BankStatementRepository;
import com.rayllanderson.raybank.utils.NumberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final CreditCardService creditCardService;
    private final BankStatementRepository bankStatementRepository;
    private final UserFinderService userFinderService;

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

    @Transactional
    public void transfer(BankTransferDto transaction){
        BankAccount senderAccount = transaction.getSender().getBankAccount();
        boolean isTransactionValid = senderAccount.getBalance().compareTo(transaction.getAmount()) > 0;
        if(isTransactionValid) {
            User recipient = this.findUserByPixOrAccountNumber(transaction);
            BankAccount recipientAccount = recipient.getBankAccount();
            BigDecimal amountToBeTransferred = transaction.getAmount();
            senderAccount.transferTo(recipientAccount, amountToBeTransferred);
            bankAccountRepository.save(senderAccount);
            bankAccountRepository.save(recipientAccount);
            bankStatementRepository.save(BankStatement.createTransferType(amountToBeTransferred, senderAccount, recipientAccount));
        } else
            throw new BadRequestException("Valor da transferência é maior que o saldo bancário");
    }

    private User findUserByPixOrAccountNumber(BankTransferDto transaction){
        String recipientPixKey = transaction.getTo();
        int recipientAccountNumber = Integer.parseInt(recipientPixKey);
        return userFinderService.findByPixOrAccountNumber(recipientPixKey, recipientAccountNumber);
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
