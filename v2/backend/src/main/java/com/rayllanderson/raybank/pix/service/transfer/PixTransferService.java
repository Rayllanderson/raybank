package com.rayllanderson.raybank.pix.service.transfer;

import com.rayllanderson.raybank.bankaccount.facades.credit.CreditAccountFacade;
import com.rayllanderson.raybank.bankaccount.facades.credit.CreditAccountFacadeInput;
import com.rayllanderson.raybank.bankaccount.facades.debit.DebitAccountFacade;
import com.rayllanderson.raybank.bankaccount.facades.debit.DebitAccountFacadeInput;
import com.rayllanderson.raybank.contact.aop.AddCreditAccountAsContact;
import com.rayllanderson.raybank.pix.gateway.PixGateway;
import com.rayllanderson.raybank.pix.model.Pix;
import com.rayllanderson.raybank.pix.model.key.PixKey;
import com.rayllanderson.raybank.pix.service.limit.CheckLimitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PixTransferService {

    private final PixGateway pixGateway;
    private final CheckLimitService limitService;
    private final PixTransferMapper pixTransferMapper;
    private final DebitAccountFacade debitAccountFacade;
    private final CreditAccountFacade creditAccountFacade;

    @Transactional
    @AddCreditAccountAsContact
    public PixTransferOutput transfer(final PixTransferInput transfer) {
        final PixKey debitKey = pixGateway.findKeyByAccountId(transfer.getDebitAccountId());
        final PixKey creditKey = pixGateway.findKeyByKey(transfer.getCreditKey());

        limitService.checkLimit(debitKey, transfer.getAmount());

        final Pix pix = Pix.newTransfer(debitKey, creditKey, transfer.getAmount(), transfer.getMessage());

        final var debit = DebitAccountFacadeInput.transfer(pix);
        final var debitTransaction = debitAccountFacade.process(debit);

        final var credit = CreditAccountFacadeInput.transfer(pix, debitTransaction.getId());
        creditAccountFacade.process(credit);

        pixGateway.save(pix);

        return pixTransferMapper.from(pix, debitTransaction.getId());
    }
}
