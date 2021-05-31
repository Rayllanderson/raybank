package com.rayllanderson.raybank.services;

import com.rayllanderson.raybank.dtos.requests.bank.BankTransferDto;
import com.rayllanderson.raybank.exceptions.BadRequestException;
import com.rayllanderson.raybank.models.BankTransfer;
import com.rayllanderson.raybank.models.User;
import com.rayllanderson.raybank.repositories.BankTransferRepository;
import com.rayllanderson.raybank.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BankTransferService {

    private final UserRepository userRepository;
    private final BankTransferRepository bankTransferRepository;

    @Transactional
    public BankTransfer transfer(BankTransferDto transaction){
        boolean isTransactionValid = transaction.getSender().getBankAccount().getBalance().compareTo(transaction.getAmount()) > 0;
        if(isTransactionValid) {
            String recipientPixKey = transaction.getTo();
            Integer recipientAccountNumber = Integer.parseInt(recipientPixKey);
            User recipient = userRepository
                    .findByPixKeysKeyOrBankAccountAccountNumber(recipientPixKey, recipientAccountNumber)
                    .orElseThrow(() -> new BadRequestException("Pix ou número da conta estão incorretos ou destinatário não existe"));
            BankTransfer bankTransfer = BankTransferDto.toBankTransfer(transaction);
            bankTransfer.setTo(recipient);
            return bankTransferRepository.save(bankTransfer);
        } else
            throw new BadRequestException("Valor da transferência é maior que o saldo bancário");
    }

}
