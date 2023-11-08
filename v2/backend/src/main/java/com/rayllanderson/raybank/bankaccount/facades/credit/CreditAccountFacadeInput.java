package com.rayllanderson.raybank.bankaccount.facades.credit;

import com.rayllanderson.raybank.bankaccount.services.credit.CreditAccountInput;
import com.rayllanderson.raybank.boleto.models.Boleto;
import com.rayllanderson.raybank.boleto.services.credit.BoletoCreditInput;
import com.rayllanderson.raybank.card.events.CardCreditPaymentCompletedEvent;
import com.rayllanderson.raybank.card.events.CardDebitPaymentCompletedEvent;
import com.rayllanderson.raybank.pix.model.Pix;
import com.rayllanderson.raybank.pix.model.PixReturn;
import com.rayllanderson.raybank.shared.dtos.Origin;
import com.rayllanderson.raybank.shared.dtos.Type;
import com.rayllanderson.raybank.transaction.models.Transaction;
import com.rayllanderson.raybank.transaction.models.TransactionMethod;
import com.rayllanderson.raybank.transaction.models.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class CreditAccountFacadeInput {

    private String accountId;
    private BigDecimal amount;
    private Origin origin;
    private final TransactionType transactionType;
    private final TransactionMethod transactionMethod;

    public CreditAccountInput toServiceInput() {
        return new ModelMapper().map(this, CreditAccountInput.class);
    }

    public static CreditAccountFacadeInput createFromCardPayment(CardCreditPaymentCompletedEvent event) {
        final var origin = new Origin(event.getCardId(), Type.CREDIT_CARD, event.getTransactionId());
        return new CreditAccountFacadeInput(event.getEstablishmentId(), event.getTotal(), origin, TransactionType.DEPOSIT, TransactionMethod.RAYBANK_TRANSFER);
    }

    public static CreditAccountFacadeInput createFromCardPayment(CardDebitPaymentCompletedEvent event) {
        final var origin = new Origin(event.getCardId(), Type.CREDIT_CARD, event.getTransactionId());
        return new CreditAccountFacadeInput(event.getEstablishmentId(), event.getTotal(), origin, TransactionType.DEPOSIT, TransactionMethod.RAYBANK_TRANSFER);
    }

    public static CreditAccountFacadeInput createFromDebitCardPayment(CardDebitPaymentCompletedEvent event) {
        final var origin = new Origin(event.getCardId(), Type.DEBIT_CARD, event.getTransactionId());
        return new CreditAccountFacadeInput(event.getEstablishmentId(), event.getTotal(), origin, TransactionType.DEPOSIT, TransactionMethod.RAYBANK_TRANSFER);
    }

    public static CreditAccountFacadeInput refundCardPayment(Transaction transaction, String debitTransactionId, BigDecimal amount) {
        final var origin = new Origin(transaction.getCredit().getId(), Type.ESTABLISHMENT_ACCOUNT, debitTransactionId);
        return new CreditAccountFacadeInput(transaction.getDebit().getId(), amount, origin, TransactionType.REFUND, TransactionMethod.DEBIT_CARD);
    }

    public static CreditAccountFacadeInput from(BoletoCreditInput boleto) {
        final var origin = new Origin(boleto.getBarCode(), Type.BOLETO, boleto.getOriginalTransactionId());
        return new CreditAccountFacadeInput(boleto.getBeneficiaryId(), boleto.getAmount(), origin, TransactionType.DEPOSIT, TransactionMethod.BOLETO);
    }

    public static CreditAccountFacadeInput refundBoleto(final Boleto boleto, final String originalTransactionId) {
        final var origin = new Origin(boleto.getBarCode(), Type.BOLETO, originalTransactionId);
        return new CreditAccountFacadeInput(boleto.getPayerId(), boleto.getValue(), origin, TransactionType.REFUND, TransactionMethod.BOLETO);
    }

    public static CreditAccountFacadeInput transfer(final Pix pix, final String originalTransactionId) {
        final var origin = new Origin(pix.getId(), Type.PIX, originalTransactionId);
        return new CreditAccountFacadeInput(pix.getCreditAccountId(), pix.getAmount(), origin, TransactionType.TRANSFER, TransactionMethod.PIX);
    }

    public static CreditAccountFacadeInput credit(final Pix pix, final String originalTransactionId) {
        final var origin = new Origin(pix.getId(), Type.PIX, originalTransactionId);
        return new CreditAccountFacadeInput(pix.getCreditAccountId(), pix.getAmount(), origin, TransactionType.DEPOSIT, TransactionMethod.PIX);
    }

    public static CreditAccountFacadeInput doReturn(final Pix pix, PixReturn pixReturn, final String originalTransactionId) {
        final var origin = new Origin(pixReturn.getId(), Type.PIX, originalTransactionId);
        return new CreditAccountFacadeInput(pix.getDebitAccountId(), pixReturn.getAmount(), origin, TransactionType.RETURN, TransactionMethod.PIX);
    }
}
