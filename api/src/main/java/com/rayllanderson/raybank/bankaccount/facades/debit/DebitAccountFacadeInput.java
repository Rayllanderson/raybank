package com.rayllanderson.raybank.bankaccount.facades.debit;

import com.rayllanderson.raybank.boleto.models.Boleto;
import com.rayllanderson.raybank.card.models.Card;
import com.rayllanderson.raybank.card.services.payment.CardPaymentInput;
import com.rayllanderson.raybank.invoice.services.credit.InvoiceCreditInput;
import com.rayllanderson.raybank.pix.model.Pix;
import com.rayllanderson.raybank.pix.model.PixReturn;
import com.rayllanderson.raybank.shared.dtos.Destination;
import com.rayllanderson.raybank.shared.dtos.Type;
import com.rayllanderson.raybank.transaction.models.Transaction;
import com.rayllanderson.raybank.transaction.models.TransactionMethod;
import com.rayllanderson.raybank.transaction.models.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class DebitAccountFacadeInput {

    private final String accountId;
    private final BigDecimal amount;
    private String description;
    private final DebitTransaction transaction;
    private final Destination destination;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class DebitTransaction {
        private String referenceId;
        private TransactionType transactionType;
        private TransactionMethod transactionMethod;

        private static DebitTransaction from(TransactionType transactionType, TransactionMethod transactionMethod) {
            return new DebitTransaction(null, transactionType, transactionMethod);
        }
    }

    public static DebitAccountFacadeInput from(final InvoiceCreditInput input) {
        final var destination = new Destination(input.getInvoiceId(), Type.INVOICE);
        final var debitTransaction = DebitTransaction.from(TransactionType.PAYMENT, TransactionMethod.ACCOUNT);
        return new DebitAccountFacadeInput(input.getAccountId(), input.getAmount(), debitTransaction, destination);
    }

    public static DebitAccountFacadeInput debitCardPayment(final CardPaymentInput input, final Card card) {
        final var destination = new Destination(input.getEstablishmentId(), Type.ACCOUNT);
        final var debitTransaction = DebitTransaction.from(TransactionType.PAYMENT, TransactionMethod.DEBIT_CARD);
        return new DebitAccountFacadeInput(card.getAccountId(), input.getAmount(), input.getDescription(), debitTransaction, destination);
    }

    public static DebitAccountFacadeInput fromRefundCardCredit(Transaction transaction, BigDecimal amount) {
        final var destination = new Destination(transaction.getDebit().getId(), Type.CREDIT_CARD);
        final var debitTransaction = new DebitTransaction(transaction.getId(), TransactionType.REFUND, TransactionMethod.CREDIT_CARD);
        return new DebitAccountFacadeInput(transaction.getCredit().getId(), amount, debitTransaction, destination);
    }

    public static DebitAccountFacadeInput from(String accountId, Boleto boleto) {
        final Destination destination = new Destination(boleto.getBarCode(), Type.BOLETO);
        final var debitTransaction = DebitTransaction.from(TransactionType.PAYMENT, TransactionMethod.BOLETO);
        return new DebitAccountFacadeInput(accountId, boleto.getValue(), debitTransaction, destination);
    }

    public static DebitAccountFacadeInput transfer(final Pix pix) {
        final Destination destination = new Destination(pix.getId(), Type.PIX);
        final var debitTransaction = DebitTransaction.from(TransactionType.TRANSFER, TransactionMethod.PIX);
        return new DebitAccountFacadeInput(pix.getDebitAccountId(), pix.getAmount(), pix.getMessage(), debitTransaction, destination);
    }

    public static DebitAccountFacadeInput pay(final Pix pix) {
        final Destination destination = new Destination(pix.getId(), Type.PIX);
        final var debitTransaction = DebitTransaction.from(TransactionType.PAYMENT, TransactionMethod.PIX);
        return new DebitAccountFacadeInput(pix.getDebitAccountId(), pix.getAmount(), pix.getMessage(), debitTransaction, destination);
    }

    public static DebitAccountFacadeInput returnPix(final Pix pix, PixReturn pixReturn) {
        final Destination destination = new Destination(pixReturn.getId(), Type.PIX);
        final var debitTransaction = DebitTransaction.from(TransactionType.RETURN, TransactionMethod.PIX);
        return new DebitAccountFacadeInput(pix.getCreditAccountId(), pixReturn.getAmount(), pixReturn.getMessage(), debitTransaction, destination);
    }
}
