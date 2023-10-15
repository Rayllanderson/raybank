package com.rayllanderson.raybank.pix.service.payment;

import com.rayllanderson.raybank.bankaccount.facades.credit.CreditAccountFacade;
import com.rayllanderson.raybank.bankaccount.facades.credit.CreditAccountFacadeInput;
import com.rayllanderson.raybank.bankaccount.facades.debit.DebitAccountFacade;
import com.rayllanderson.raybank.bankaccount.facades.debit.DebitAccountFacadeInput;
import com.rayllanderson.raybank.core.exceptions.UnprocessableEntityException;
import com.rayllanderson.raybank.pix.gateway.PixGateway;
import com.rayllanderson.raybank.pix.model.Pix;
import com.rayllanderson.raybank.pix.model.qrcode.PixQrCode;
import com.rayllanderson.raybank.pix.model.key.PixKey;
import com.rayllanderson.raybank.pix.service.limit.CheckLimitService;
import com.rayllanderson.raybank.transaction.models.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PixPaymentService {

    private final PixGateway pixGateway;
    private final CheckLimitService limitService;
    private final PixPaymentMapper mapper;
    private final DebitAccountFacade debitAccountFacade;
    private final CreditAccountFacade creditAccountFacade;

    @Transactional
    public PixPaymentOutput pay(final PixPaymentInput paymentInput) {
        final PixQrCode qrCode = pixGateway.findQrCodeByQrCode(paymentInput.getQrCode());

        if (qrCode.isExpired()) {
            throw UnprocessableEntityException.with("Qr Code expirado");
        }

        final PixKey debitKey = pixGateway.findKeyByAccountId(paymentInput.getDebitAccountId());
        limitService.checkLimit(debitKey, qrCode.getAmount());

        final Pix pix = Pix.newPayment(qrCode, debitKey);

        final Transaction debitTransaction = processDebit(pix);
        processCredit(pix, debitTransaction);

        pixGateway.save(pix);

        return mapper.from(pix, debitTransaction.getId());
    }

    private Transaction processDebit(Pix pix) {
        final var debit = DebitAccountFacadeInput.pay(pix);
        return debitAccountFacade.process(debit);
    }

    private void processCredit(Pix pix, Transaction debitTransaction) {
        final var credit = CreditAccountFacadeInput.credit(pix, debitTransaction.getId());
        creditAccountFacade.process(credit);
    }
}
