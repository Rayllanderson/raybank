package com.rayllanderson.raybank.bankaccount.facades;

import com.rayllanderson.raybank.bankaccount.services.DebitAccountInput;
import com.rayllanderson.raybank.card.models.Card;
import com.rayllanderson.raybank.card.services.payment.PaymentCardInput;
import com.rayllanderson.raybank.card.transactions.payment.CardCreditPaymentTransaction;
import com.rayllanderson.raybank.invoice.services.payment.InvoicePaymentInput;
import com.rayllanderson.raybank.shared.dtos.Destination;
import com.rayllanderson.raybank.shared.dtos.Type;
import com.rayllanderson.raybank.transaction.models.Transaction;
import com.rayllanderson.raybank.transaction.models.TransactionType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class DebitAccountFacadeInput {

    private final String accountId;
    private final BigDecimal amount;
    private final TransactionType transactionType;
    private final Destination destination;

    public DebitAccountInput toServiceInput() {
        return new ModelMapper().map(this, DebitAccountInput.class);
    }

    public static DebitAccountFacadeInput from(final InvoicePaymentInput input) {
        final var destination = new Destination(input.getInvoiceId(), Type.INVOICE);
        return new DebitAccountFacadeInput(input.getAccountId(), input.getAmount(), TransactionType.INVOICE_PAYMENT, destination);
    }

    public static DebitAccountFacadeInput from(final PaymentCardInput input, final Card card) {
        final var destination = new Destination(input.getEstablishmentId(), Type.ACCOUNT);
        return new DebitAccountFacadeInput(card.getAccountId(), input.getAmount(), TransactionType.DEBIT_CARD_PAYMENT, destination);
    }

    public static DebitAccountFacadeInput fromRefundCardCredit(Transaction transaction, BigDecimal amount) {
        final var destination = new Destination(transaction.getDebit().getId(), Type.CREDIT_CARD);
        return new DebitAccountFacadeInput(transaction.getCredit().getId(), amount, TransactionType.CREDIT_CARD_REFUND, destination);
    }
}
