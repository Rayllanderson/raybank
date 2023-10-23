package com.rayllanderson.raybank.bankaccount.services.transfer;

import com.rayllanderson.raybank.bankaccount.services.credit.CreditAccountMapper;
import com.rayllanderson.raybank.bankaccount.services.credit.CreditAccountService;
import com.rayllanderson.raybank.bankaccount.services.debit.DebitAccountMapper;
import com.rayllanderson.raybank.bankaccount.services.debit.DebitAccountService;
import com.rayllanderson.raybank.contact.aop.AddCreditAccountAsContact;
import com.rayllanderson.raybank.transaction.models.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BankAccountTransferService {

    private final DebitAccountMapper debitMapper;
    private final DebitAccountService debitService;

    private final CreditAccountMapper creditMapper;
    private final CreditAccountService creditService;

    @Transactional
    @AddCreditAccountAsContact
    public Transaction transfer(final BankAccountTransferInput input) {
        final var debit = debitMapper.from(input);
        final var debitTransaction = debitService.debit(debit);

        final var credit = creditMapper.from(input, debitTransaction.getId());
        this.creditService.creditByAccountNumber(credit);

        return debitTransaction;
    }
}
