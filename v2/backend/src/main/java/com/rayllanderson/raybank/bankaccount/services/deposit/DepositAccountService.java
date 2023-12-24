package com.rayllanderson.raybank.bankaccount.services.deposit;

import com.rayllanderson.raybank.bankaccount.services.credit.CreditAccountInput;
import com.rayllanderson.raybank.bankaccount.services.credit.CreditAccountMapper;
import com.rayllanderson.raybank.bankaccount.services.credit.CreditAccountService;
import com.rayllanderson.raybank.transaction.gateway.TransactionGateway;
import com.rayllanderson.raybank.transaction.models.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepositAccountService {

    private final CreditAccountService creditAccountService;
    private final CreditAccountMapper creditAccountMapper;
    private final TransactionGateway transactionGateway;

    public Transaction deposit(DepositAccountInput input) {
        final Transaction depositTransaction = transactionGateway.save(Transaction.depositAccount(input));

        CreditAccountInput deposit = creditAccountMapper.from(input, depositTransaction.getId());
        return creditAccountService.credit(deposit);
    }

}
