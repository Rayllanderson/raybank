package com.rayllanderson.raybank.pix.service.pixreturn;

import com.rayllanderson.raybank.bankaccount.facades.credit.CreditAccountFacade;
import com.rayllanderson.raybank.bankaccount.facades.credit.CreditAccountFacadeInput;
import com.rayllanderson.raybank.bankaccount.facades.debit.DebitAccountFacade;
import com.rayllanderson.raybank.bankaccount.facades.debit.DebitAccountFacadeInput;
import com.rayllanderson.raybank.core.exceptions.UnprocessableEntityException;
import com.rayllanderson.raybank.pix.gateway.PixGateway;
import com.rayllanderson.raybank.pix.model.Pix;
import com.rayllanderson.raybank.pix.model.PixReturn;
import com.rayllanderson.raybank.transaction.models.Transaction;
import com.rayllanderson.raybank.utils.MathUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.DEBIT_ORIGIN_DIFFERENT;
import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.PIX_RETURN_EXCEEDED;

@Service
@RequiredArgsConstructor
public class PixReturnService {

    private final PixGateway pixGateway;
    private final PixReturnMapper mapper;
    private final DebitAccountFacade debitAccountFacade;
    private final CreditAccountFacade creditAccountFacade;

    @Transactional
    public PixReturnOutput doReturn(final PixReturnInput pixReturnInput) {
        final var pix = pixGateway.findPixById(pixReturnInput.getPixId());

        final boolean debitOriginDifferent = !(pix.getCredit().sameAccount(pixReturnInput.getReturningAccountId()));
        if(debitOriginDifferent) {
            throw UnprocessableEntityException.with(DEBIT_ORIGIN_DIFFERENT, "Não é possível devolver Pix de outra pessoa");
        }

        validateAmount(pixReturnInput, pix);

        final PixReturn pixReturn = PixReturn.newReturn(pixReturnInput.getAmount(), pixReturnInput.getMessage(), pix);

        final Transaction debitTransaction = processDebit(pix, pixReturn);
        processCredit(pix, pixReturn, debitTransaction);

        pixGateway.save(pixReturn);

        return mapper.from(pix, pixReturn, debitTransaction.getId());
    }

    private void validateAmount(PixReturnInput pixReturnInput, Pix pix) {
        final List<PixReturn> allPixReturns = pixGateway.findAllPixReturnByPixId(pixReturnInput.getPixId());
        final BigDecimal amountReturned = MathUtils.sum(allPixReturns.stream().map(PixReturn::getAmount));

        if (amountReturned.add(pixReturnInput.getAmount()).compareTo(pix.getAmount()) > 0) {
            throw UnprocessableEntityException
                    .withFormatted(PIX_RETURN_EXCEEDED, "Valor a devolver é maior que a Transação Pix. Valor já devolvido R$ %s. Valor do Pix %s", amountReturned, pix.getAmount());
        }
    }

    private Transaction processDebit(Pix pix, PixReturn pixReturn) {
        final var debit = DebitAccountFacadeInput.returnPix(pix, pixReturn);
        return debitAccountFacade.process(debit);
    }

    private void processCredit(Pix pix, PixReturn pixReturn, Transaction debitTransaction) {
        final var credit = CreditAccountFacadeInput.doReturn(pix, pixReturn, debitTransaction.getId());
        creditAccountFacade.process(credit);
    }

}
