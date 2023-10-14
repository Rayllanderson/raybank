package com.rayllanderson.raybank.pix.service.transfer;

import com.rayllanderson.raybank.bankaccount.facades.credit.CreditAccountFacadeInput;
import com.rayllanderson.raybank.bankaccount.facades.debit.DebitAccountFacade;
import com.rayllanderson.raybank.bankaccount.facades.credit.CreditAccountFacade;
import com.rayllanderson.raybank.bankaccount.facades.debit.DebitAccountFacadeInput;
import com.rayllanderson.raybank.core.exceptions.UnprocessableEntityException;
import com.rayllanderson.raybank.pix.gateway.PixGateway;
import com.rayllanderson.raybank.pix.model.Pix;
import com.rayllanderson.raybank.pix.model.key.PixKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PixTransferService {

    private final PixGateway pixGateway;
    private final PixTransferMapper pixTransferMapper;
    private final DebitAccountFacade debitAccountFacade;
    private final CreditAccountFacade creditAccountFacade;

    @Transactional
    public PixTransferOutput transfer(PixTransferInput transfer) {
        final PixKey debitKey = pixGateway.findKeyByAccountId(transfer.getDebitAccountId());
        final PixKey creditKey = pixGateway.findByKey(transfer.getCreditKey());

        checkLimit(debitKey, transfer.getAmount());

        final Pix pix = Pix.newTransfer(debitKey, creditKey, transfer.getAmount(), transfer.getMessage());

        final var debit = DebitAccountFacadeInput.transfer(pix);
        final var debitTransaction = debitAccountFacade.process(debit);

        final var credit = CreditAccountFacadeInput.transfer(pix, debitTransaction.getId());
        creditAccountFacade.process(credit);

        pixGateway.save(pix);

        return pixTransferMapper.from(pix, debitTransaction.getId());
    }

    private void checkLimit(final PixKey debitKey, final BigDecimal transactionAmount) {
        final var limit = pixGateway.findLimitByAccountId(debitKey.getAccountId());
        if (!limit.hasLimitFor(transactionAmount)) {
            throw UnprocessableEntityException.withFormatted("Limite insuficiente para transação. Seu limite disponível é de %s", limit.getLimit());
        }
    }
}
