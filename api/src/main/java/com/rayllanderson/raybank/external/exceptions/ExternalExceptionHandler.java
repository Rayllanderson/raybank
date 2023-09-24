package com.rayllanderson.raybank.external.exceptions;

import com.rayllanderson.raybank.external.payment.models.ExternalTransactionSituation;
import com.rayllanderson.raybank.external.payment.repositories.ExternalTransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

import static com.rayllanderson.raybank.external.payment.models.ExternalTransactionStatus.ERROR;

@Slf4j
@Order(1)
@RestControllerAdvice(basePackages = "com.rayllanderson.raybank.external")
@RequiredArgsConstructor
public class ExternalExceptionHandler {

    private final ExternalTransactionRepository externalTransactionRepository;

    @ExceptionHandler(RaybankExternalException.class)
    public ResponseEntity<ExternalErrorResponse> handleExternalException(RaybankExternalException e) {
        log.error("Handling external exception: {}", e.toString());
        return ResponseEntity.status(e.getStatus()).body(ExternalErrorResponse.fromExternalException(e));
    }

    @ExceptionHandler(value = {
            RaybankExternalException.DebitCardNotFound.class,
            RaybankExternalException.CreditCardNotFound.class,
            RaybankExternalException.CardBadlyFormatted.class,
            RaybankExternalException.InsufficientAccountBalance.class,
            RaybankExternalException.InsufficientCreditCardLimit.class
    })
    public ResponseEntity<ExternalErrorResponse> handleExternalException(RaybankExternalException e, HttpServletRequest request) {
        log.error("Handling external exception: {}", e.toString());
        var possibleBankStatement = e.getExternalTransaction();
        if(possibleBankStatement.isPresent()) {
            var bankStatement = possibleBankStatement.get();
            var situation = new ExternalTransactionSituation(ERROR, e.getDescription());
            bankStatement.setSituation(situation);
            log.error("Salvando {} com status {}", bankStatement, ERROR);
            externalTransactionRepository.save(bankStatement);
        }
        return ResponseEntity.status(e.getStatus()).body(ExternalErrorResponse.fromExternalException(e));
    }
}
