package com.rayllanderson.raybank.card.services.refund.debit;

import com.rayllanderson.raybank.bankaccount.facades.credit.CreditAccountFacade;
import com.rayllanderson.raybank.bankaccount.facades.credit.CreditAccountFacadeInput;
import com.rayllanderson.raybank.bankaccount.facades.debit.DebitAccountFacade;
import com.rayllanderson.raybank.bankaccount.facades.debit.DebitAccountFacadeInput;
import com.rayllanderson.raybank.core.exceptions.InternalServerErrorException;
import com.rayllanderson.raybank.core.exceptions.NotFoundException;
import com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason;
import com.rayllanderson.raybank.core.exceptions.UnprocessableEntityException;
import com.rayllanderson.raybank.refund.service.RefundOutput;
import com.rayllanderson.raybank.refund.service.strategies.RefundCommand;
import com.rayllanderson.raybank.refund.service.strategies.RefundService;
import com.rayllanderson.raybank.transaction.gateway.TransactionGateway;
import com.rayllanderson.raybank.transaction.models.Transaction;
import com.rayllanderson.raybank.transaction.models.TransactionMethod;
import com.rayllanderson.raybank.transaction.models.TransactionType;
import com.rayllanderson.raybank.utils.MathUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.REFUND_AMOUNT_HIGHER;

@Service
@RequiredArgsConstructor
public class RefundCardDebitService implements RefundService {

    private final DebitAccountFacade debitAccountFacade;
    private final CreditAccountFacade creditAccountFacade;
    private final TransactionGateway transactionGateway;

    @Override
    @Transactional
    public RefundOutput process(final RefundCommand command) {
        final var originalTransaction = command.getTransaction();
        final var establishmentTransaction = transactionGateway.findByReferenceIdAndAccountId(originalTransaction.getId(), originalTransaction.getCredit().getId())
                .orElseThrow(() -> InternalServerErrorException.with(RaybankExceptionReason.TRANSACTION_NOT_FOUND, "Nenhuma transação de débito em estabelecimento encontrada"));

        List<Transaction> allRefunds = transactionGateway.findAllByReferenceIdAndType(establishmentTransaction.getId(), TransactionType.REFUND);

        final var totalSum = MathUtils.sum(allRefunds.stream().map(Transaction::getAmount));
        if (totalSum.add(command.getAmount()).compareTo(originalTransaction.getAmount()) > 0) {
            throw UnprocessableEntityException.with(REFUND_AMOUNT_HIGHER, "Valor a reembolsar somando os valores já reembolsados, ultrapassa o valor da transação");
        }

        DebitAccountFacadeInput debit = DebitAccountFacadeInput.refundCardPayment(originalTransaction, command.getAmount());
        Transaction debitTransaction = debitAccountFacade.process(debit);

        final var credit = CreditAccountFacadeInput.refundCardPayment(originalTransaction, debitTransaction.getId(), command.getAmount());
        creditAccountFacade.process(credit);

        return new RefundOutput(originalTransaction.getId(), command.getAmount());
    }

    @Override
    public boolean supports(final RefundCommand command) {
        return command.getTransaction().getMethod().equals(TransactionMethod.DEBIT_CARD) &&
                command.getTransaction().getType().equals(TransactionType.PAYMENT);
    }
}
